def getRandomNum(int range_end = 10) {
    int random = sh(returnStdout: true, script: "shuf -i 1-${range_end} -n 1")
    return random
}

node("master") {
    ws("workspace/" + env.BUILD_TAG) {
        stage("Checkout") {
            checkout scm: [$class: 'GitSCM',
                           branches: [[name: '9dd9e0e99ede422008ce4e88047ee9ef24263cac']],
                           userRemoteConfigs: [[url: 'https://github.com/junit-team/junit5-samples.git']],
                           extensions: [[$class: 'CloneOption', noTags: true]]
            ]
        }
        dir("junit5-vanilla-maven") {
            stage("Build") {
                sh("""
                    cat <<'EOF' > src/test/java/com/example/project/CalculatorTests.java
                    package com.example.project;
                    import static org.junit.jupiter.api.Assertions.assertEquals;
                    import org.junit.jupiter.api.Test;
                    
                    class CalculatorTests {
                    
                        @Test
                        void test_1() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_2() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_3() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_4() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_5() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_6() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_7() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum()}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_8() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum(5)}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                        
                        @Test
                        void test_9() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum(5)}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }

                        @Test
                        void test_10() {
                                Calculator calculator = new Calculator();
                                assertEquals(${this.getRandomNum(5)}, calculator.add(1, 1), "1 + 1 should equal 2");
                        }
                    }
                    EOF""".stripIndent())
            }
            stage("Run tests") {
                try {
                    // Run tests
                    withEnv(["PATH+MVN=/usr/share/maven/bin", "M3_HOME=${env.WORKSPACE}/.m2"]) {
                        sh("mvn -Dmaven.repo.local='${env.WORKSPACE}/.m2/repository' -B clean verify")
                    }
                }
                catch(Throwable e) {
                    // Set build status
                    currentBuild.result = "UNSTABLE"
                }
            }
            stage("Publish test results") {
                // Publish results
                junit 'target/surefire-reports/*.xml'
            }
        }
        deleteDir()
    }
}
