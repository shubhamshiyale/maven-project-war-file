// This is the jenkins-file to cicd
//this is a second commit 
pipeline{
    agent any
    stages{
        stage('stage-checkout'){
            steps{
                dir("${WORKSPACE}"){
                    script{
                    println 'git checkout started'
                    sh 'git clone https://github.com/shubhamshiyale/maven-project-war-file.git'.execute()
                    }
                }
            }
        }
    }
}