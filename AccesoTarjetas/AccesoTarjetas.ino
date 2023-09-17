#include "SPI.h"
#include "MFRC522.h"

#define RST_PIN  9 // RES pin
#define SS_PIN  10 // SS pin
const int ledPIN = 2;

byte readCard[4];
String cardID = "49A89C23"; // sustitúyalo por el ID de su etiqueta
String tagID = "";

MFRC522 mfrc522(SS_PIN, RST_PIN); 

void setup() {
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  //mfrc522.PCD_DumpVersionToSerial();
  pinMode(5, OUTPUT);
  pinMode(ledPIN , OUTPUT);
}

void loop() {

if (! mfrc522.PICC_IsNewCardPresent()) {
    return false;
  }

  if (! mfrc522.PICC_ReadCardSerial()) {
    return false;
  }

  tagID = "";
  
  for (uint8_t i = 0; i < 4; i++) {
    tagID.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  tagID.toUpperCase();
  mfrc522.PICC_HaltA();
  Serial.println(tagID);
  digitalWrite(ledPIN , HIGH);
  delay(1000);
  digitalWrite(ledPIN , LOW);
}
