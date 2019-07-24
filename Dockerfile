FROM jboss/wildfly
ADD ./target/discordee.war /opt/jboss/wildfly/standalone/deployments/
