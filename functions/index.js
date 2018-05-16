'use strict'
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendNotification = functions.database.ref('/notification/{user_id}/{notification_id}').onWrite((change, context) => {
    const user_id = context.params.user_id;
    const notification_id = context.params.notification_id;
    console.log('The notificationid is : ', notification_id);

    const userFrom = admin.database().ref('/notification/' + user_id + '/' + notification_id).once('value');

    return userFrom.then(function(snapshot1){
        const userFromId = snapshot1.child("sent_authId").val();
        console.log('sent uid is :', userFromId);

        const sentNickName = admin.database().ref('/user/' + userFromId + '/userNickName').once('value');
        return sentNickName.then(function(snapsho2){
            const userFromNickName = snapsho2.val();
            console.log(userFromNickName);

            const messageToken = admin.database().ref('/user/' + user_id + '/messageToken').once('value');
            return messageToken.then(function(snapshot){
    
                const token = snapshot.val();
                console.log(token);
                
                const status = admin.database().ref('/user/' + user_id + '/status').once('value');
                return status.then(function(snapshot3){
                    const statusNumber = snapshot3.val();

                    if(statusNumber < 1){
                        const payload = {
                            notification: {
                                title: "Want You!",
                                body: "wants to join you tonight!",
                                icon:"default"
                            },
                            data: {
                                "message_id": String(notification_id),
                                "sent_nickname": String(userFromNickName)
                            }
                        };
                
                        return admin.messaging().sendToDevice(token ,  payload).then(response => {
                            return console.log('this is the notification');
                        }); 
                    }else{
                        return console.log('no disturb');
                    }
                    
                });
    
            });
    
        });



        });

       
  
});
