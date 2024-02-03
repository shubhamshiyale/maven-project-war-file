pipeline{
    agent {
        label 'DOCKER'
    }
    stages{
        stage('stage checkout'){
            steps{
               dir("${WORKSPACE}"){
                 script{
                    cleanWs()
                    println('checkout git repo')
                    sh"""
                        echo "${${GIT-repo}}"
                        git clone https://github.com/shubhamshiyale/"${GIT-repo}".git

                    """
                 }
               }
            }
           
        }
        stage('build'){
            steps{
                dir("${WORKSPACE}"){
                script{
                    sh """
                       cd "${GIT-repo}"
                       mvn install
                    """
                }
                }
            }
        }
        stage('build image'){
            steps{
                dir("${WORKSPACE}"){
                    script{
                        sh """
                           cd "${GIT-repo}"
                           docker build -t "${GIT-repo}"-image .
                        """
                    }
                }
            }
        }
        stage('deploy'){
            steps{
                dir("${WORKSPACE}"){
                    script{
                        sh"""
                          docker images 
                          docker run -itdp 8080:8080 "${GIT-repo}"-image                           
                        """
                    }
                }
            }
        }
    }
}