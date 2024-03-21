node {
  stage('SCM') {
    checkout scm
  }
    stage('SonarQube Analysis lngtpa') {
        def mvn = tool 'maven3.8.6';
        def scannerHome = tool 'sonarqube_4.7';
        def sonarlogin = 'admin'
        def sonarpassword = 'oPjH63.fh2_f23Hvc81Jf'
        def sonarjavabinaries = '/usr/lib/jvm/java-11-openjdk-11.0.16.0.8-1.el7_9.x86_64/'
        def sonarprojectkey = 'atosawg_tpagnl-project_tpagnl_AYYrcZ0yeRl6HymQ3Jdp'
    /*    withSonarQubeEnv() {
            sh "${mvn}/bin/mvn sonar:sonar -Dsonar.login=${sonarlogin} -Dsonar.password=${sonarpassword} -Dsonar.java.binaries=${sonarjavabinaries}"
        }*/
    }
 
}
pipeline {
    environment {
        BRANCH_NAME =  "${GIT_BRANCH.split("/")[1]}"
    }
    agent any

    stages {
        stage('build') {
            steps {
                echo "Building..${BRANCH_NAME}"
                updateGitlabCommitStatus name: 'build', state: 'pending'
                script {
                    try {
                        sh "/opt/maven/apache-maven-3.8.6/bin/mvn -f tpa/pom.xml -U -Ptst2 clean install"
                        updateGitlabCommitStatus name: 'build', state: 'success'
                    } catch (Exception e) {
                        echo 'Exception occurred: ' + e.toString()
                        updateGitlabCommitStatus name: 'build', state: 'failed'
                        sh 'exit 1'
                    }
                }
            }
        }
        stage('test') {
            steps {
                echo "Testing..${BRANCH_NAME}"
                updateGitlabCommitStatus name: 'test', state: 'pending'
                script {
                    try {
                        //sh ""
                        updateGitlabCommitStatus name: 'test', state: 'success'
                    } catch (Exception e) {
                        echo 'Exception occurred: ' + e.toString()
                        updateGitlabCommitStatus name: 'test', state: 'failed'
                        sh 'exit 1'
                    }
                }
            }
        }
        stage('deploy') {
            steps {
                echo "Deploying..${BRANCH_NAME}"
                updateGitlabCommitStatus name: 'deploy', state: 'pending'
                script {
                    try {
	                     sshPublisher(
						   continueOnError: false, failOnError: true,
						   alwaysPublishFromMaster: false,
						   publishers: [
						    sshPublisherDesc(
						     configName: "tpasystem_weblogic",
						     verbose: true,
						     transfers: [
						      sshTransfer(
						       excludes: '',
						       sourceFiles: "tpa/tpa-ear/target/tpa-ear.ear",
						       removePrefix: "tpa/tpa-ear/target",
						       remoteDirectory: "",
						       remoteDirectorySDF : false,
						       execCommand: 'export CLASSPATH=/u01/app/oracle/middleware/wlserver/server/lib/weblogic.jar;java weblogic.Deployer -adminurl t3://TPA1-wls-0:9071 -user weblogic -password 7HdFCSa98KoNbF2cH -undeploy -name tpa-ear -targets TPA1_cluster;java weblogic.Deployer -adminurl t3://TPA1-wls-0:9071 -user weblogic -password 7HdFCSa98KoNbF2cH -deploy -name tpa-ear -targets TPA1_cluster -source /home/opc/deploy/tpa-ear.ear -remote -upload'
						      )
						     ])
						   ])
                        updateGitlabCommitStatus name: 'deploy', state: 'success'
                    } catch (Exception e) {
                        echo 'Exception occurred: ' + e.toString()
                        updateGitlabCommitStatus name: 'deploy', state: 'failed'
                        sh 'exit 1'
                    }
                }
            }
        }
    }
}
