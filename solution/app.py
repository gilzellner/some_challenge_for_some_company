import datetime
from flask import Flask
from jenkinsapi.jenkins import Jenkins
from prettytable import PrettyTable

app = Flask(__name__)
J = Jenkins('http://localhost:8080', username='admin', password='admin')
job=J['promote-master']


@app.route('/dashboard')
def dashboard():
	total_builds = job.get_last_buildnumber()
	column_names = ['tests/builds'] + (range(1,total_builds)[::-1])
	t = PrettyTable()
	test_names = []
	resultset = job.get_build_metadata(buildnumber=total_builds).get_resultset()._data
	for test_result in resultset['suites'][0]['cases']:
		test_names.append(test_result['className'] + '.' + test_result['name'])
	t.add_column(column_names[0], test_names)
	for build_number in range(1, total_builds):
		resultset = job.get_build_metadata(buildnumber=build_number).get_resultset()._data
		test_results = []
		for test_result in resultset['suites'][0]['cases']:
			test_results.append(test_result['status'])
		print test_results
		t.add_column(str(build_number), test_results)
	return str(t)


@app.route('/build')
def build():
	largest = datetime.timedelta(0,0)
	index_largest = 0
	for i in range(1, job.get_last_buildnumber()):
		build = job.get_build_metadata(buildnumber=i)
		current = build.get_duration()
		if current > largest:
			index_largest = i
			largest = current
	result = "Out of " + str(job.get_last_buildnumber()) + \
			 " builds, build number " + str(index_largest) + \
			 " took the longest to run: " + str(largest) + " seconds"
	return result

@app.route('/test')
def test():
	longest_index = -1
	longest_name = 'faketest'
	longest_duration = -1
	total_builds = job.get_last_buildnumber()
	for build_number in range(1, total_builds):
		resultset = job.get_build_metadata(buildnumber=build_number).get_resultset()._data
		for test_result in resultset['suites'][0]['cases']:
			current_duration = test_result['duration']
			if current_duration > longest_duration:
				longest_index = build_number
				longest_name = test_result['className'] + '.' + test_result['name']
				longest_duration = current_duration
	result = 'Out of ' + str(total_builds) + ' builds, build number ' + \
				str(longest_index) + ' had the longest running test: \n' + \
				'Test name: ' + longest_name + '\n' + \
				'Test duration: ' + str(longest_duration) + ' seconds \n'
	return str(result)