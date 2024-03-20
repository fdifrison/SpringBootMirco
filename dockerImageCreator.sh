cd accounts || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..

cd cards || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..

cd loans || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..

cd config-server || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..

cd gateway-server || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..

cd service-discovery-agent || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..

cd message || exit

mvn clean install -DskipTests

mvn compile jib:dockerBuild -DskipTests

cd ..