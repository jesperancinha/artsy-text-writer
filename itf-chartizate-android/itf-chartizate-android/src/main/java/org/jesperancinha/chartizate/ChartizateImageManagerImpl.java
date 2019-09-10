package org.jesperancinha.chartizate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import org.jesperancinha.chartizate.objects.ChartizateCharacterImg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author joao
 */
public class ChartizateImageManagerImpl extends ChartizateImageManager<Integer, Typeface, Bitmap> {

    private Bitmap bitmap;
    private final String outputFile;

    public ChartizateImageManagerImpl(final InputStream inputStream, String outputFile) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        try {
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
        }
        this.outputFile = outputFile;
    }

    @Override
    public int getImageWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getImageHeight() {
        return bitmap.getHeight();
    }

    @Override
    public int getImagePixelRGB(int x, int y) {
        return bitmap.getPixel(x, y);
    }

    @Override
    public Integer createColor(int alpha, int red, int green, int blue) {
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public Bitmap generateBufferedImage(
            ChartizateCharacterImg<?>[][] chartizateCharacterImgs,
            ChartizateFontManager<Typeface> chartizateFontManager,
            int outputWidth,
            int outputHeight
    ) throws IOException {

        final Bitmap bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setTextSize(chartizateFontManager.getFontSize());
        paint.setTypeface(chartizateFontManager.getFont());
        paint.setColor(((ChartizateCharacterImg<Integer>) chartizateCharacterImgs[0][0]).getBg());
        canvas.drawPaint(paint);
        int currentWidth = 0;
        for (int i = 0; i < chartizateCharacterImgs.length; i++) {
            int rowLength = chartizateCharacterImgs[i].length;
            for (int j = 0; j < rowLength; j++) {
                final ChartizateCharacterImg<Integer> character = (ChartizateCharacterImg<Integer>) chartizateCharacterImgs[i][j];
                paint.setColor(character.getFg());
                canvas.drawText(character.toString(), (float) currentWidth, (float) (chartizateFontManager.getFontSize() * (i + 1)), paint);
                currentWidth += chartizateCharacterImgs[i][j].getWidth();
            }
            currentWidth = 0;
        }
        final FileOutputStream out = new FileOutputStream(this.outputFile);
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
            Log.i("File", this.outputFile.concat(" is saved"));
        }
        return bitmap;
    }

    @Override
    public int getBlue(int color) {
        return Color.blue(color);
    }

    @Override
    public int getGreen(int color) {
        return Color.green(color);
    }

    @Override
    public int getRed(int color) {
        return Color.red(color);
    }

    @Override
    public int getAlpha(int color) {
        return Color.alpha(color);
    }
}
