import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MastercardIpmIncomingFileProcessing {

    byte BLOCK_CHARACTER = 0x00;
    static byte TRAILING_CHARACTER = 0x00;        //default

    private static void printHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString());
    }


    public static void main(String args[]) {
        String fileName = "MCI.AR.T112.M.E0035443.D230929.T062552.A005";
        String filePath = "/Users/yogeshnaik/Yogesh/workspace/mc-sftp-cloudedge/" + fileName;
        File file = new File(filePath);

        TRAILING_CHARACTER = '@';        //added this based on the file we are going to parse

        fileName = file.getName();
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.print("Original :");
        printHexString(fileContent);
        System.out.println("Original Length :" + fileContent.length);

        byte[] newByte = new byte[fileContent.length - (2 * (fileContent.length / 1012))];

        /*
         * This loop removes @@ or NULL characters after every 1012 bytes
         */
        int count = 0, k = 0;
        for (int i = 0, j = 0; i < fileContent.length / 1012; i++) {
            k = 0;
            while (k < 1012) {
                newByte[count++] = fileContent[j++];
                k++;
            }
            j = j + 2;
        }
        //System.out.println(newByte.length);
        System.out.print("After removing Block charachters @@ or NULL characters after every 1012 bytes :");
        printHexString(newByte);

        System.out.println("Length :" + newByte.length);

        /*
         * This loop removes trailing NULL or an MasterCard defined Character
         */
        int finalBufferSize = newByte.length - 1;
        while (finalBufferSize > 0) {
            if (newByte[finalBufferSize] != TRAILING_CHARACTER) {
                finalBufferSize++;
                break;
            }
            finalBufferSize--;
        }
        System.out.println("finalBufferSize : " + finalBufferSize);

        if (TRAILING_CHARACTER != 0x00) {
            finalBufferSize--;
            while (finalBufferSize > 0) {
                if (newByte[finalBufferSize] != 0x00) {
                    finalBufferSize++;
                    break;
                }
                finalBufferSize--;
            }
        }
        System.out.println("finalBufferSize : " + finalBufferSize);

        /*
         * Prepare Final buffer to be processed
         */
        byte[] dataBuffer = new byte[finalBufferSize];
        for (k = 0; k < finalBufferSize; k++) {
            dataBuffer[k] = newByte[k];
        }
        System.out.print("Final Buffer : ");
        printHexString(dataBuffer);

        processRecordsInBuffer(dataBuffer);

    }

    public static void processRecordsInBuffer(byte[] dataBuffer) {
        int i = 0;
        while (i < dataBuffer.length) {
            //System.out.println("i : ["+i+"], dataBuffer.length : ["+dataBuffer.length+"]");
            StringBuilder sb = new StringBuilder();
            for (int z = 0; z < 4; z++) {
                sb.append(String.format("%02X", dataBuffer[i++]));
            }
            //System.out.println(sb.length());
            System.out.println("\nRecord Length: " + sb.toString());


            int messageLength = Integer.parseInt(sb.toString(), 16);
            //System.out.println(String.format("%02X ",messageLength));
            System.out.println("Message Length in Decimal: " + messageLength);

            byte[] messageBuffer = new byte[messageLength];
            for (int j = 0; j < messageLength; j++) {
                messageBuffer[j] = dataBuffer[i++];
            }
            //System.out.println(messageBuffer.length);
            printHexString(messageBuffer);

            /*
             * IsoMessageStream isoMessageStream = new IsoMessageStream();
             * isoMessageStream.FromBytes(null, messageBuffer, messageLength);
             */


        }
    }
}
