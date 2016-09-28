package forsakenharmony.gameprot.utils

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils

/**
 * @author ArmyOfAnarchists
 */
class ShapeRenderer : ShapeRenderer {
  
  constructor() : super(5000) {
  }
  
  override fun arc(x: Float, y: Float, radius: Float, start: Float, degrees: Float, segments: Int) {
    if (currentType != ShapeType.Line) {
      super.arc(x, y, radius, start, degrees, segments)
      return
    }
    
    if (segments <= 0) throw IllegalArgumentException("segments must be > 0.")
    val colorBits = color.toFloatBits()
    val theta = 2f * MathUtils.PI * (degrees / 360.0f) / segments
    val cos = MathUtils.cos(theta)
    val sin = MathUtils.sin(theta)
    var cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians)
    var cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians)

//        renderer.color(colorBits)
//        renderer.vertex(x, y, 0f)
//        renderer.color(colorBits)
//        renderer.vertex(x + cx, y + cy, 0f)
    for (i in 0..segments - 1) {
      renderer.color(colorBits)
      renderer.vertex(x + cx, y + cy, 0f)
      val temp = cx
      cx = cos * cx - sin * cy
      cy = sin * temp + cos * cy
      renderer.color(colorBits)
      renderer.vertex(x + cx, y + cy, 0f)
    }
//        renderer.color(colorBits)
//        renderer.vertex(x + cx, y + cy, 0f)
//
//        val temp = cx
//        cx = 0f
//        cy = 0f
//        renderer.color(colorBits)
//        renderer.vertex(x + cx, y + cy, 0f)
  }
}