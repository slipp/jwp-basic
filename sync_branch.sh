#!/bin/bash

REPOSITORY_DIR=~/jwp-workspace/cherry-pick/jwp-basic

BRANCHES=("step0-getting-started" "step1-user-completed-no-database" "step2-user-with-mvc-framework" "step3-user-completed-database" "step4-qna-getting-started" "step5-qna-with-ajax" "step6-qna-with-mvc-framework" "step7-self-check" "step8-self-check-completed" "step9-mvc-framework-completed" "step10-di-getting-started" "step11-1st-di-framework" "step12-2nd-di-framework" "step13-3rd-di-framework" "step14-di-framework-completed" "step15-spring-orm-framework")
COMMIT_REVISION=a33b298

rm -rf $REPOSITORY_DIR

git clone https://github.com/slipp/jwp-basic.git

cd $REPOSITORY_DIR
pwd

for BRANCH_NAME in "${BRANCHES[@]}"; do
	git cherry-pick --quit
    echo $BRANCH_NAME
    git checkout -b $BRANCH_NAME origin/$BRANCH_NAME
    git cherry-pick $COMMIT_REVISION
    git push origin $BRANCH_NAME 
done

echo "Finished."