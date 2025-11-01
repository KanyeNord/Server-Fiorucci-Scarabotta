# Server di Daniele Fiorucci e Alessio Scarabotta

## Presentazione

Il nostro server funziona da host per una chat di gruppo, dove dei client possono semplicemente collegarsi e condividere i loro messaggi.

## Progettazione

Il server è stato scritto con il linguaggio Java, nell'IDE Eclipse. 

## Installazione

### Scaricare il necessario

- Scaricare Tomcat 10.1.48
(https://tomcat.apache.org/download-10.cgi)

- Scaricare Eclipse IDE for Enterprise Java and Web Developers, la nostra versione è la 2025-09
(https://www.eclipse.org/downloads/packages/release/2025-09/r/eclipse-ide-enterprise-java-and-web-developers)

- Scaricare il full JDK 21 di liberica
(https://bell-sw.com/pages/downloads/#jdk-21-lts)

### Configurare il JDK

Una volta aperto eclipse, per utilizzare il JDK di liberica, bisogna andare in alto a sinistra su 
window > preferences > Java e cliccare su Installed JREs, poi una volta nella schermata cliccare su Add… > next > Directory… e cercare la cartella estratta del JDK (dovrebbe chiamarsi qualcosa del tipo "jdk-21.0.9-full"), una volta selezionata cliccare su finish e selezionare  sulla lista dei JREs il jdk appena aggiunto, infine cliccare su "Apply and close"

### Configurare Tomcat

Per installare il server bisogna andare sulla tabella Servers nella sezione in basso di eclipse (se non presente aggiungerla da Window > Show View) e cliccare su "No servers are available. Click this link to create a new server..."
selezionare il server "Tomcat v10.1 Server" cliccare su next, poi su Browse... e selezionare la cartella in cui è installato Tomcat, infine cliccare su finish.