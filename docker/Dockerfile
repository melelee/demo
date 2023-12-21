FROM centos:7.9.2009
MAINTAINER melelee<melelee@qq.com>

ENV path /
WORKDIR $path

#?
VOLUME /temp
#?
RUN bash -c 'touch /*.jar'

ADD jdk-8u381-linux-x64.rpm $path
RUN yum install -y jdk-8u381-linux-x64.rpm \
rm -rf jdk-8u381-linux-x64.rpm
yum install -y net-tools
yum install -y lsof
yum install -y vim

#EXPOSE 80:80
CMD /bin/bash
