package org.jesperancinha.chartizate;

import org.jesperancinha.chartizate.objects.ChartizateCharacterImg;

import java.io.IOException;

public abstract class ChartizateImageManagerAbstract<C, F, B> implements ChartizateImageManager<C, F, B> {

    private static class ColorHelper {
        double alpha = 0;
        double red = 0;
        double green = 0;
        double blue = 0;

        public void addAlpha(double alpha) {
            this.alpha += alpha;
        }

        public void addRed(double red) {
            this.red += red;
        }

        public void addGreen(double green) {
            this.green += green;
        }

        public void addBlue(double blue) {
            this.blue += blue;
        }
    }

    public C getImageAverageColor() {
        final int width = getImageWidth() - 1;
        final int height = getImageHeight() - 1;
        return getPartAverageColor(0, 0, width, height);
    }

    public C getPartAverageColor(final int x0, final int y0, final int xn, final int yn) {

        final ColorHelper colorHelper = new ColorHelper();
        for (int j = x0; j <= xn && j < getImageWidth(); j++) {
            for (int k = y0; k <= yn && k < getImageHeight(); k++) {
                int rgbPixel = getImagePixelRGB(j, k);
                colorHelper.addAlpha(getAlpha(rgbPixel));
                colorHelper.addRed(getRed(rgbPixel));
                colorHelper.addGreen(getGreen(rgbPixel));
                colorHelper.addBlue(getBlue(rgbPixel));
            }
        }
        int commonDenominator = (xn - x0 + 1) * (yn - y0 + 1);
        return createColor(
                colorHelper.alpha / commonDenominator,
                colorHelper.red / commonDenominator,
                colorHelper.green / commonDenominator,
                colorHelper.blue / commonDenominator);
    }

    public abstract int getBlue(int rgbPixel);

    public abstract int getGreen(int rgbPixel);

    public abstract int getRed(int rgbPixel);

    public abstract int getAlpha(int rgbPixel);

    public abstract void saveBitmap(B bufferedImage) throws IOException;

    public abstract int getImagePixelRGB(int j, int k);

    public abstract C createColor(double mediumApha, double mediumRed, double mediumGreen, double mediumBlue);

    public abstract int getImageWidth();

    public abstract int getImageHeight();

    public abstract B generateBufferedImage(ChartizateCharacterImg<C>[][] chartizateCharacterImage, ChartizateFontManager<F> fontManager) throws IOException;
}