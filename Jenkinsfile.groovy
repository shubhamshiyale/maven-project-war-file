// This is the jenkins-file to cicd
//this is a second commit 
pipeline{
    agent any
    stages{
        stage('stage-checkout'){
            steps{
                script{
                    dir("${WORKSPACE}"){
                    println 'git checkout started'
                    clone = "git clone https://github.com/shubhamshiyale/maven-project-war-file.git".execute()
                    }
                }
            }
        }
    }
}