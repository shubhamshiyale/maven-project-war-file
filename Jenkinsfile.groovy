// This is the jenkins-file to cicd
//this is a second commit 
pipeline{
    agent{
        label 'Built-In'
    }
    stages{
        stage('stage-checkout'){
            steps{
                dir("${WORKSPACE}"){
                    script{
                    cleanWs()
                    println 'git checkout started'
                    sh 'git clone https://github.com/shubhamshiyale/servlettomcatsample.git -b master'
                    }
                }
            }
        }
        stage('stage-build'){
            steps{
                dir("${WORKSPACE}"){
                    script{
                        try{
                        println 'building the project'
                        sh """

                        cd ${WORKSPACE}/servlettomcatsample/servlettomcatsample
                        ls -ltra
                        mvn install 
                        """
                        }
                        catch(Exception e){
                            error 'build failed due to error :' + "${e}"
                        }
                    }
                }
            }
        }
    }
}