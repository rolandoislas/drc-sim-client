package com.rolandoislas.drcsimclient.net.packet;

import com.rolandoislas.drcsimclient.data.Constants;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by rolando on 5/29/17.
 */
public class CommandPacket {
    public final Header header;

    public CommandPacket(DatagramPacket packet) {
        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());
        header = new Header();
        header.type = buffer.getShort(0);
        header.payloadSize = buffer.getShort(2);
        header.payload = Arrays.copyOfRange(packet.getData(), 4, packet.getLength());
        switch (header.type) {
            case Constants.COMMAND_REGISTER:
            case Constants.COMMAND_PING:
            case Constants.COMMAND_PONG:
            case Constants.COMMAND_INPUT_VIBRATE:
            case Constants.COMMAND_INPUT_MIC_BLOW:
            case Constants.COMMAND_INPUT_BUTTON:
            case Constants.COMMAND_INPUT_JOYSTICK:
            case Constants.COMMAND_INPUT_TOUCH:
            case Constants.COMMAND_INPUT_BUTTON_EXTRA:
            default:
                break;
        }
    }

    public static byte[] create(short id) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(0, id); // type
        buffer.putShort(2, (short) 0); // payload_size
        return buffer.array();
    }

    public static byte[] create_button(short buttonBits) {
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.putShort(0, Constants.COMMAND_INPUT_BUTTON); // type
        buffer.putShort(2, (short) 2); // payload_size
        buffer.putShort(4, buttonBits);
        return buffer.array();
    }

    public static byte[] create_joystick(short left_x, short left_y, short right_x, short right_y) {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putShort(0, Constants.COMMAND_INPUT_JOYSTICK); // type
        buffer.putShort(2, (short) 8); // payload_size
        buffer.putShort(4, left_x);
        buffer.putShort(6, left_y);
        buffer.putShort(8, right_x);
        buffer.putShort(10, right_y);
        return buffer.array();
    }

    public static byte[] create_touch(short x, short y, short width, short height) {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putShort(0, Constants.COMMAND_INPUT_TOUCH); // type
        buffer.putShort(2, (short) 8); // payload_size
        buffer.putShort(4, x);
        buffer.putShort(6, y);
        buffer.putShort(8, width);
        buffer.putShort(10, height);
        return buffer.array();
    }

    public static byte[] create_button_extra(short extraButtonBits) {
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.putShort(0, Constants.COMMAND_INPUT_BUTTON_EXTRA); // type
        buffer.putShort(2, (short) 2); // payload_size
        buffer.putShort(4, extraButtonBits);
        return buffer.array();
    }

    public final class Header {
        public short type;
        public short payloadSize;
        public byte payload[];
    }

}
