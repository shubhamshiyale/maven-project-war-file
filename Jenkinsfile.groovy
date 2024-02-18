// This is the jenkins-file to cicd
//this is a second commit 
pipeline{
    agent{
        label 'Jenkins-master'
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
        stage('store-artifact'){
            steps{
                dir("${WORKSPACE}/servlettomcatsample/servlettomcatsample/target"){
                    script{
                        sh """
                             aws s3 cp *war s3://build-artifacts-shubham

                        """
                    }
                }
            }
        }
        stage('deploy'){
            steps{
                agent{
                    label 'QA'
                }
                dir("${WORKSPACE}/deploy"){
                    script{
                      sh """
                      aws s3 cp s3://build-artifacts-shubham/*war --latest /mnt/tomcat/apache-tomcat-9.0.85/webapps
                      echo "deploying arifact"
                      cp *war /mnt/tomcat/apache-tomcat-9.0.85/webapps

                      """
                    }
                }
            }
        }
    }
}