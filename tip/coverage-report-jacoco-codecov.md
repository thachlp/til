#### Add Jacoco plugin to generate coverage report
```groovy
plugins {
    id 'jacoco'
}

test {
   finalizedBy jacocoTestReport // report is always generated after tests run
}
jacocoTestReport {
   dependsOn test // tests are required to run before generating the report
   reports {
      xml.required = true // generate for codecov viewing
   }
}
```
#### Getting a Codecov account and uploading coverage
1. Create a Codecov account at [signup page](https://about.codecov.io/sign-up/)

2. Install the GitHub App Integration at [github app](https://github.com/apps/codecov)

3. Add CI pipeline
```yaml
  - name: Upload coverage reports to Codecov
    uses: codecov/codecov-action@v3
    env:
      CODECOV_TOKEN: ${{ secrets.CODECOV_TOKEN }} #Get from (1), add to GitHub secret. 
```
