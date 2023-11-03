# android-api-sample
## App Overview
This app serves as a client for the [express-sample-api](https://github.com/kazu09/express-sample-api). It's designed as a verification app that sends requests to the API and displays the responses for testing and validation purposes.

## App Requirements:
To use this app, you need to connect to the same network as the [express-sample-api](https://github.com/kazu09/express-sample-api).

## Screen Description.
### UserId
  This is assuming the database field's Id.
### Name
  This is assuming the database field's Name.
### mail
  This is assuming the database field's email.
### Message
  Currently not in use.
### Url
  It is necessary to match the URL log displayed when Express is started.
  Without a match, communication with the [express-sample-api](https://github.com/kazu09/express-sample-api) won't be possible.
  Please make sure to enter the URL before pressing the "Call API"button.

## Request Types.
### GET
Please enter the userId when making a GET request.
### ALL GET
No parameters are required for the request.
### POST
Please enter the name and mail when making a POST request.
### PUT
Please enter the userId and mail, name when making a PUT request.
### DELETE
Please enter the userId when making a DELETE request.

## Create XML file
Please create an XML file.<br>
app/src/main/res/xml/network_security_config.xml<br>

## Network Security Configuration.
Ensure to configure this file to match your network requirements and add necessary permissions for the app to communicate with the API.
```
network_security_config.xml

<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">your IP (ex : 127.0.0.1)</domain>
    </domain-config>
</network-security-config>
```
