stage 'pull'
node('jenkins_slave') {
    sh '''yum install git -y
        '''
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/radupantiru/k8test.git']]])
    dir('k8test') {
        sh '''echo "hello world"
              ls -R
        '''
    }
    stash include: "k8test", name: "docker_hello_world"
}
stage 'master_code'
node('master') {
    unstash "docker_hello_world"
    sh '''echo "hello world"
        ls -R
    '''
}
