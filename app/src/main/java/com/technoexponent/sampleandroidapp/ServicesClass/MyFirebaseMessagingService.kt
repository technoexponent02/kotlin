package com.technoexponent.sampleandroidapp.ServicesClass

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.technoexponent.sampleandroidapp.R
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by technoexponent on 07-Dec-17.
 */
public class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO: Handle FCM messages here.

        Log.d(TAG, "From: " + remoteMessage.getFrom())
        Log.d(TAG, "remoteMessage: " + remoteMessage)

//        Log.d(TAG, "Notification Message Title: " + remoteMessage.getNotification()!!.title)
       // Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification()!!.getBody())


      //  Log.e("From2: ", remoteMessage.data.toString())
      //  Log.d(TAG, "message: " + remoteMessage.data["message"])
      //  Log.e("fcmmsg", "fcmmsg")
     //   Log.e("fcmmsg", remoteMessage.data["message"])

        if(remoteMessage.getData().size > 0){
            //handle the data message here


        }

      /*  try {

            val jsonObject = JSONObject(remoteMessage.data["dataContent"])
            val type = jsonObject.getString("type")

            System.out.println("Birendra ::"+jsonObject)
        }catch (ex:Exception)
        {
            ex.printStackTrace()
        }*/

         // sendNotification(remoteMessage)
       //  sendMessage()

    }


  /*  override fun handleIntent(intent: Intent) {
        try {
            if (intent.extras != null) {
                val builder = RemoteMessage.Builder("MyFirebaseMessagingService")

                for (key in intent.extras!!.keySet()) {
                    builder.addData(key, intent.extras!!.get(key)!!.toString())
                }
                onMessageReceived(builder.build())
            } else {
                super.handleIntent(intent)
            }
        } catch (e: Exception) {
            super.handleIntent(intent)
        }

    }
*/
    /**
     * Send notification
     */
    private fun sendNotification(remoteMessage: RemoteMessage) {
        try {
            val jsonObject = JSONObject(remoteMessage.data["dataContent"])
            val notiType = jsonObject.getString("noti_type")
            val messageType = jsonObject.getString("title")
            if (messageType != null) {
                // Sets an ID for the notification
                val mNotificationId = 1
                val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                // Build Notification , setOngoing keeps the notification always in status bar
                val mBuilder = NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_add_black_32dp)
                        .setContentTitle(remoteMessage.getNotification()!!.title)
                        .setStyle(NotificationCompat.BigTextStyle()
                                .bigText(messageType))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setOngoing(false)
                // Gets an instance of the NotificationManager service
                val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                // Builds the notification and issues it.
                mNotifyMgr.notify(mNotificationId, mBuilder.build())
            }
            else if (notiType!=null && notiType.equals(""))
            {

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    companion object {
        private val TAG = "FCM Service"
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
    // be received by the ReceiverActivity.
    private fun sendMessage() {
        Log.e("sender", "Broadcasting message")
        val intent = Intent("notification-count")
        // You can also include some extra data.
        intent.putExtra("message", "notification count!")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }


}