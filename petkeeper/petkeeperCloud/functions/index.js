/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

// const {onRequest} = require("firebase-functions/v2/https");
// const logger = require("firebase-functions/logger");

const functions = require("firebase-functions");
// The Firebase Admin SDK to access cloud Firestore
const admin = require("firebase-admin");
admin.initializeApp();

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

exports.helloWorld = functions.https.onRequest((request, response) => {
  functions.logger.info("Hello logs!", {structuredData: true});
  response.send("Hello from Firebase!");
});

// to add users every time a new user is authenticated

exports.addUserToFirestore = functions.auth.user().onCreate((user) =>{
// Code that runs every time anew user is created
  const usersRef = admin.firestore().collection("users");
  return usersRef.doc(user.uid).set({
    userId: user.userId,
    userName: user.userName,
    profileImageUrl: user.profileImageUrl,
  });
});


