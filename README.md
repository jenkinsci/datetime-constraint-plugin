https://github.com/Jouda-Hidri/curfew-hpi/blob/master/0.1.0/curfew.hpi?raw=true# Curfew

Curfew is a Jenkins plugin that enables you to set (configurable through the UI) time restrictions, that can be applied to any stage in the pipeline, by simply calling the method `curfew()`. If someone triggers a Jenkins pipeline outside of your allowed timeframe, the pipeline will be aborted, unless manually approved through the UI prompt.

### Contents
1. [Getting started](#Getting-started)
2. [Jenkinsfile examples](#Jenkinsfile-examples)
3. [Showcase](#Showcase)

## Getting started

* Download the hpi file locally: [click here](https://github.com/Jouda-Hidri/curfew-hpi/blob/master/0.1.0/curfew.hpi?raw=true)
* Go to **Manage Jenkins** > **Manage Plugins** > **Advanced** > **Upload plugin**
* Click on **choose file**, navigate to where you download the .hpi file and select it. Curfew will be now installed as a plugin within Jenkins.
* Go back to **Manage Jenkins** > **Configure System**
* Find Curfew and set your configuration (timezone, restricted times/days).
* In your Jenkinsfile simply call `curfew` at any stage

**Settings example:** (Mon-Thu prevent before 08:00 and after 16:00, Fri time restriction all day, no restrictions Sat-Sun).
<img src="./screenshots/config_example.png" width="800" alt="Curfew UI Demo">

## Jenkinsfile examples

**Declarative Pipeline**

```
pipeline {
  agent any
  stages {
    stage('curfew') {
      steps {
        script {
          curfew()
        }
      }
    }
  }
}
```

**Scripted Pipeline**

```
node {
  stage('curfew') {
    curfew()
  }
}
```

## Showcase

Following you will find a demonstration of what the prompt for manual approval looks like, in case someone triggers the pipeline outside of the allowed timeframe:

<img src="./screenshots/pipeline_abort.png" width="350" alt="Curfew UI Demo">

## License
This project is licensed under the MIT License.
