package geek.marbles.entity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.Random;

/** Replacement to avoid using {@link java.awt.Color} which breaks renders
 *
 * Created by Robin Seifert on 12/24/2021.
 */
public class MarbleColor
{
    public final byte red;
    public final byte green;
    public final byte blue;

    public MarbleColor(int red, int green, int blue)
    {
        this((byte)red, (byte)green, (byte)blue);
    }

    public MarbleColor(byte red, byte green, byte blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public MarbleColor(int rgb) {
        red = (byte) ((rgb >> 16) & 0xFF);
        green = (byte) ((rgb >> 8) & 0xFF);
        blue = (byte) (rgb & 0xFF);
    }

    public int toInt() {
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }

    public static MarbleColor randomColor(Random random) {
        //to get rainbow, pastel colors https://stackoverflow.com/questions/4246351/creating-random-colour-in-java
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 0.5f; //1.0 for brighter, 0.0 for black
        return HSBtoRGB(hue, saturation, luminance);
    }

    /**
     * From java.awt.Color... copied and modified to avoid loading awt module
     */
    private static MarbleColor HSBtoRGB(float hue, float saturation, float brightness) {
        byte r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (byte) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (byte) (brightness * 255.0f + 0.5f);
                    g = (byte) (t * 255.0f + 0.5f);
                    b = (byte) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (byte) (q * 255.0f + 0.5f);
                    g = (byte) (brightness * 255.0f + 0.5f);
                    b = (byte) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (byte) (p * 255.0f + 0.5f);
                    g = (byte) (brightness * 255.0f + 0.5f);
                    b = (byte) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (byte) (p * 255.0f + 0.5f);
                    g = (byte) (q * 255.0f + 0.5f);
                    b = (byte) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (byte) (t * 255.0f + 0.5f);
                    g = (byte) (p * 255.0f + 0.5f);
                    b = (byte) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (byte) (brightness * 255.0f + 0.5f);
                    g = (byte) (p * 255.0f + 0.5f);
                    b = (byte) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return new MarbleColor(r, g, b);
    }

    public MarbleColor copy() {
        return new MarbleColor(red, green, blue);
    }

    public static final EntityDataSerializer<MarbleColor> ENTITY_DATA_SYNC = new EntityDataSerializer<MarbleColor>() {

        @Override
        public void write(FriendlyByteBuf buffer, MarbleColor color) {
            buffer.writeByte(color.red);
            buffer.writeByte(color.green);
            buffer.writeByte(color.blue);
        }

        @Override
        public MarbleColor read(FriendlyByteBuf buffer) {
            return new MarbleColor(buffer.readByte(), buffer.readByte(), buffer.readByte());
        }

        @Override
        public MarbleColor copy(MarbleColor color) {
            return color.copy();
        }
    };
}
