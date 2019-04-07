/* This file describes a generic device that can be connected to the network.  
    The model of the device should be set when it is created and it does not 
    seem to make sense to change it later, hence the lack of a set model method*/

/* A device is a node in the network, hence the fact that it extends the node class*/
package starlan.GenericClasses;

/**
 *
 * @author mweya
 */
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import starlan.misc.Address;
import starlan.misc.Node;
import java.io.BufferedOutputStream;
import starlan.ErrorClasses.InvalidAddressException;
public class Device<AnyType> extends Node {
    private String address;
    private String model;
    // The router device refers to the device we are connected to.
    // Might be a good idea to think of it as one hop up the chain
    private Device router;
    //private BufferedOutputStream console = new BufferedOutputStream();
    
    public Device() {}
    
    public Device(String address) {
        this.address = address;
    }
    
    public Device(String address, String model) {
        this.address = address;
        this.model = model;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public String getModel() {
        return this.model;
    }
    
    public void setAddress(String address) throws InvalidAddressException {
        if (Address.isLegal(address)) {
            this.address = address;
        } else {
            throw new InvalidAddressException();
        }
    }
    
    // Required by the document
    public void send(String destination, AnyType data) {
        // Make a packet to send and then send it
        // Default behaviour is to add the sender's information to the packet when it's sent
        Packet p = new Packet(destination, this.address, data);
        sendPacket(p);
    }
    
    // This method will be overidden by the Server class if needed
    public void recievePacket(Packet packet) {
        // Check to see if we should have the packet
        if (this.address.equals(packet.getDestination())){
            // Read the info inside the packet
            handleData((AnyType) packet.getData(), packet.getSource());
        }
        // If we shouldn't have this packet, ignore it
    }
    
    public void sendPacket(Packet packet) {
        
    }
    
    // This should be used to display the information recieved by the 
    // device
    private void writeToConsole(String s) {
         DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         Date date = new Date();
         
    }
    
    // Get a dump of the console's state (everything that has been written to it
    public void readConsole() {
        
    }
    
    // ToDo
    public void handleData(AnyType data, String source) {
        // This is temporary, should actually write to the device's 
        // output stream
        System.out.println("Packet recieved from "+source+"\n"+data);
    }
    
    @Override
    public String toString() {
        return model+" is at "+address+"\n";
    }
}
