package io.discovery.util;

import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码干扰线和干扰点
 *
 * @author fzx
 * @date 2018/11/21
 */
public class NoisePoint extends Configurable implements NoiseProducer {
  /**
   * Draws AuthorizationTaobaoForm noise on the image. The noise curve depends on the factor values.
   * Noise won't be visible if all factors have the value > 1.0f
   *
   * @param image       the image to add the noise to
   * @param factorOne
   * @param factorTwo
   * @param factorThree
   * @param factorFour
   */
  @Override
  public void makeNoise(BufferedImage image, float factorOne,
                        float factorTwo, float factorThree, float factorFour) {
    Color color = getConfig().getNoiseColor();

    // image size
    int width = image.getWidth();
    int height = image.getHeight();
    Random rand = new Random();
    Graphics2D graph = (Graphics2D) image.getGraphics();

    // 随机产生干扰点
    int pointCount = 20;
    for (int i = 0; i < pointCount; i++) {
      int x = rand.nextInt(width);
      int y = rand.nextInt(height);
      graph.setColor(new Color(0, 0, 0));
      graph.drawOval(x, y, rand.nextInt(3), rand.nextInt(3));
    }
    // the points where the line changes the stroke and direction
    Point2D[] pts = null;

    // the curve from where the points are taken
    CubicCurve2D cc = new CubicCurve2D.Float(width * factorOne, height
        * rand.nextFloat(), width * factorTwo, height
        * rand.nextFloat(), width * factorThree, height
        * rand.nextFloat(), width * factorFour, height
        * rand.nextFloat());

    // creates an iterator to define the boundary of the flattened curve
    PathIterator pi = cc.getPathIterator(null, 2);
    Point2D[] tmp = new Point2D[200];
    int i = 0;

    // while pi is iterating the curve, adds points to tmp array
    while (!pi.isDone()) {
      float[] coords = new float[6];
      switch (pi.currentSegment(coords)) {
        case PathIterator.SEG_MOVETO:
        case PathIterator.SEG_LINETO:
          tmp[i] = new Point2D.Float(coords[0], coords[1]);
        default:

      }
      i++;
      pi.next();
    }

    pts = new Point2D[i];
    System.arraycopy(tmp, 0, pts, 0, i);

    graph.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

    graph.setColor(color);

    // for the maximum 3 point change the stroke and direction
    for (i = 0; i < pts.length - 1; i++) {
      if (i < 3) {
        graph.setStroke(new BasicStroke(0.9f * (4 - i)));
      }

      graph.drawLine((int) pts[i].getX(), (int) pts[i].getY(),
          (int) pts[i + 1].getX(), (int) pts[i + 1].getY());
    }

    graph.dispose();
  }
}
