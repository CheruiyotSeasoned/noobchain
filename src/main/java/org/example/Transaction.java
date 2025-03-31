package org.example;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

    public String transactionId;
    public PublicKey sender;
    public PublicKey recipient;
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0;
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.recipient = to;
        this.value= value;
        this.inputs = inputs;
    }

    private String calculateHash(){
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(recipient) +
                        Float.toString(value) + sequence
        );
    }
    public void generateSignature(PrivateKey privateKey){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient)
                + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey,data);
    }
    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(sender)+StringUtil.getStringFromKey(recipient)
                + Float.toString(value);
        return  StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction() {

        if(verifySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        //Gathers transaction inputs (Making sure they are unspent):
        for(TransactionInput i : inputs) {
            i.UTXO = NoobChain.UTXOs.get(i.transactionOutputId);
        }

        //Checks if transaction is valid:
        if(getInputsValues() < NoobChain.minimumTransaction) {
            System.out.println("Transaction Inputs too small: " + getInputsValues());
            System.out.println("Please enter the amount greater than " + NoobChain.minimumTransaction);
            return false;
        }

        //Generate transaction outputs:
        float leftOver = getInputsValues() - value; //get value of inputs then the left over change:
        transactionId = calculateHash();
        outputs.add(new TransactionOutput( this.recipient, value,transactionId)); //send value to recipient
        outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender

        //Add outputs to Unspent list
        for(TransactionOutput o : outputs) {
            NoobChain.UTXOs.put(o.id , o);
        }

        //Remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            NoobChain.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }
    //return sum of input UTXOs values
    public float getInputsValues(){
        float total = 0;
        for (TransactionInput i :  inputs) {
            if (i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }
    public float getOutputsValues(){
        float total = 0;
        for (TransactionOutput o :  outputs) {
            total += o.value;
        }
        return total;
    }

}
