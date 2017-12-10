git pull origin master
git submodule foreach git checkout master &&  git pull origin master
mvn clean install
docker build --force-rm -t node1:5000/ebuy-login-service .
docker push node1:5000/ebuy-login-service
