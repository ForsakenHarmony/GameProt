package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import forsakenharmony.gameprot.components.*
import forsakenharmony.gameprot.utils.Constants
import forsakenharmony.gameprot.utils.Constants.UI_DIST
import forsakenharmony.gameprot.utils.ShapeRenderer

/**
 * @author ArmyOfAnarchists
 */
class UISystem : IteratingSystem {
  
  private var cam: OrthographicCamera
  private val shapeRenderer: ShapeRenderer
  private val batch: SpriteBatch
  
  private val um: ComponentMapper<UIComponent>
  private val tm: ComponentMapper<TransformComponent>
  private val im: ComponentMapper<InputComponent>
  private val sm: ComponentMapper<ShipComponent>
  private val cm: ComponentMapper<CameraComponent>
  
  private var players: Array<Entity> = Array()
  
  constructor(camera: OrthographicCamera, batch: SpriteBatch) : super(Family.all(UIComponent::class.java, TransformComponent::class.java).get()) {
    this.cam = camera
    this.batch = batch
  }
  
  init {
    shapeRenderer = ShapeRenderer()
    shapeRenderer.setAutoShapeType(true)
    
    um = ComponentMapper.getFor(UIComponent::class.java)
    tm = ComponentMapper.getFor(TransformComponent::class.java)
    im = ComponentMapper.getFor(InputComponent::class.java)
    sm = ComponentMapper.getFor(ShipComponent::class.java)
    cm = ComponentMapper.getFor(CameraComponent::class.java)
  }
  
  override fun update(deltaTime: Float) {
    super.update(deltaTime)
    
    if (players.size == 0) return
    
    var i = 0
    for (player in players) {
      if (!cm.has(player)) continue
      
      if (players.size == 1) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
      } else if (players.size == 2) {
        Gdx.gl.glViewport(i * Gdx.graphics.width / 2, 0, Gdx.graphics.width / 2, Gdx.graphics.height)
      }
      
      val camera = cm.get(player)
      cam.position.set(camera.position)
      cam.update()
      
      shapeRenderer.projectionMatrix = cam.combined
      batch.projectionMatrix = cam.combined
      
      shapeRenderer.begin()
      batch.begin()
      
      for (entity in entities) {
        if (entity.equals(player)) continue
        else {
          drawEnemy(entity, player)
        }
      }
      
      drawPlayer(player)
      
      batch.end()
      shapeRenderer.end()
      
      i++
    }
    players.clear()
  }
  
  fun drawEnemy(entity: Entity, player: Entity) {
    val transform = tm.get(entity)
//                    val rotation = transform.rotation + 90 % 360
    
    val playerTransform = tm.get(player)
    
    if (transform.pos.dst(playerTransform.pos) <= UI_DIST) return
    
    val shipIcon = sm.get(entity).iconTexture
    
    val rot = Math.atan2((transform.y - playerTransform.y).toDouble(), (transform.x - playerTransform.x).toDouble()).toFloat() * MathUtils.radiansToDegrees
    val pos = Vector2(UI_DIST, 0f).rotate(rot)
    
    val width: Float = shipIcon.regionWidth.toFloat()
    val height: Float = shipIcon.regionHeight.toFloat()
    val originX: Float = width * 0.5f
    val originY: Float = height * 0.5f
    
    batch.draw(shipIcon
        , playerTransform.x + pos.x - originX, playerTransform.y + pos.y - originY
        , originX, originY
        , width, height
        , 1f * Constants.PIXELS_TO_METRES, 1f * Constants.PIXELS_TO_METRES
        , rot - 90f)
  }
  
  fun drawPlayer(player: Entity) {
    shapeRenderer.setColor(1f, 0f, 1f, 0f)
    
    val transform = tm.get(player)
//            val rotation = transform.rotation + 90 % 360
    shapeRenderer.circle(transform.x, transform.y, UI_DIST - 0.2f, 40)
  }
  
  override fun processEntity(entity: Entity?, deltaTime: Float) {
    if (im.has(entity)) {
      players.add(entity)
    }
  }
}