#!/bin/bash

BRANCH_NAME=step11-1st-di-framework
COMMIT_REVISION=cc52eef

git checkout $BRANCH_NAME

git cherry-pick $COMMIT_REVISION

git push origin $BRANCH_NAME

echo "Finished."