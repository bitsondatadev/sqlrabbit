# SQL Rabbit 🐇

SQL Rabbit is a training tool for SQL to test and share SQL problems and their solutions. 

This is the complete source code and management configuration for the site running at <http://sqlrabb.it>


The orignal code derives from [the SQLFiddle project](https://github.com/zzzprojects/sqlfiddle3) which uses [the MIT License](https://github.com/zzzprojects/sqlfiddle3/blob/master/LICENSE). Contributions are welcome!

---

## Running SQL Rabbit

SQL Rabbit uses Terraform to make bootstrapping simpler. Here are some dependencies you'll need to install if you want to run SQL Rabbit on your own server.

### Requirements

* Install Terraform >= 1.0.0
* Docker
* kubectl
* k3ds


### Building Docker images

You can build the container images with these commands:

    docker build -t sqlfiddle/appservercore:latest appServerCore
    docker build -t sqlfiddle/varnish:latest varnish
    docker build -t sqlfiddle/appdatabase:latest appDatabase
    docker build -t sqlfiddle/hostmonitor:latest hostMonitor
    docker build -t sqlfiddle/mysql56host:latest mysql56Host
    docker build -t sqlfiddle/postgresql96host:latest postgresql96Host
    docker build -t sqlfiddle/postgresql93host:latest postgresql93Host
    docker build -t sqlfiddle/mssql2017host:latest mssql2017Host

### Starting Kubernetes services

Note that it is expected that in production, you would use an external PostgreSQL service (such as AWS RDS) to run the appDatabase. The "isLocal" switch in the helm charts is used to switch between an externally-hosted appDatabase and one running in a container.

You will need to have `helm` and `kubectl` installed before running these commands. If you installed Minikube then kubectl should already be available. Otherwise, just make sure you have it installed and configured to refer to your Kubernetes environment.

Once the container images are available to Kubernetes (from the above steps), run these commands to start up the SQL Fiddle services:

    kubectl create namespace sqlfiddle
    helm install sqlfiddleOpenCore

### Accessing your running instance

Use a browser to access the site via the Kubernetes ingress service. You can find the IP address necessary to access the site using this command:

    kubectl --namespace sqlrabbit describe ing

You can expect output like so:

    Name:             ingress
    Namespace:        sqlfiddle
    Address:          192.168.99.100
    Default backend:  appserver-service:80 (172.17.0.11:8080,172.17.0.2:8080)
    Rules:
      Host  Path  Backends
      ----  ----  --------
      *     *     appserver-service:80 (172.17.0.11:8080,172.17.0.2:8080)
    ....    

Use the value for "Address" as the host name for your URL. For example, based on the above sample output you could access the running instance at http://192.168.99.100/

## Support

Currently, this is being hosted on a server in my basement and is very dependent on power of my house. Through enough sponsorships, I hope to host this on a managed Kubernetes cluster in the cloud.

Want to help us? Your donation directly helps us maintain and grow SQL Rabbit and similar efforts. 

We can't thank you enough for your support 🙏.

❤️ [Become a sponsor](https://github.com/sponsors/bitsondatadev) 

### Why should I contribute to this free & open-source library?
We all love free and open-source libraries! But there is a catch... nothing is free in this world.

Contributions allow us to spend more of our time on: Bug Fix, Development, Documentation, and Support.

### How much should I contribute?
Any amount is much appreciated. Small amounts aggregate to large sums.

Another great free way to contribute is  **spreading the word** about the library.

A **HUGE THANKS** for your help!

