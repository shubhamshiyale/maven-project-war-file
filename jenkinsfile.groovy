pipeline{
    agent {
        label 'DOCKER'
    }
    stages{
        stage('stage checkout'){
            steps{
               dir("${WORKSPACE}"){
                 script{
                    properties([
                        parameters([
                            choice(
                                choices: ['maven-project-war-file'],
                                name: 'GIT_repo'
                            )
                        ])
                    ])
                    cleanWs()
                    println('checkout git repo')
                    sh"""
                        echo "${GIT_repo}"
                        git clone https://github.com/shubhamshiyale/"${GIT_repo}".git -b master

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
                       cd "${GIT_repo}"
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
                           cd "${GIT_repo}"
                           docker build -t "${GIT_repo}"-image .
                        """
                    }
                }
            }
        }
        stage('deploy'){
            steps{
                dir("${WORKSPACE}"){
                    script{
                        try{
                        sh"""
                          docker images 
                          docker run -itdp 8080:8080 "${GIT_repo}"                          
                        """
                        }
                        catch (Exception e){
                            throw e
                        }
                    }
                }
            }
        }
    }
    post{
        success{
            echo "sending email to devops that deploy is succcess"
        }
        failure{
            echo "sending email to devops that deploy is fail"
        }
    }
}