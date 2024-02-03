pipeline{
    agent {
        label 'DOCKER'
    }
    stages{
        stage('stage checkout'){
            steps{
               dir("${WORKSPACE}"){
                 script{
                    println('checkout git repo')
                    sh"""
                        git clone https://github.com/shubhamshiyale/${GIT-repo}.git
                        
                    """
                 }
               }
            }
           
        }
    }
}