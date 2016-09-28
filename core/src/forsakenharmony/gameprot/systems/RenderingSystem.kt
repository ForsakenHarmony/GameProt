package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import forsakenharmony.gameprot.components.BackgroundComponent
import forsakenharmony.gameprot.components.CameraComponent
import forsakenharmony.gameprot.components.TextureComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.utils.Constants
import forsakenharmony.gameprot.utils.Constants.PIXELS_TO_METRES
import java.util.*

/**
 * @author ArmyOfAnarchists
 */
class RenderingSystem : IteratingSystem {
  
  private var batch: SpriteBatch
  private val shapeRenderer: ShapeRenderer
  private val b2d: Box2DDebugRenderer
  
  private var renderQueue: Array<Entity>
  private var comparator: Comparator<Entity>
  private var cam: OrthographicCamera
  
  private var texM: ComponentMapper<TextureComponent>
  private var traM: ComponentMapper<TransformComponent>
  private var camM: ComponentMapper<CameraComponent>
  private val bacM: ComponentMapper<BackgroundComponent>
  
  private val cameras: Array<CameraComponent>
  
  private val world: World
  
  init {
    texM = ComponentMapper.getFor(TextureComponent::class.java)
    traM = ComponentMapper.getFor(TransformComponent::class.java)
    camM = ComponentMapper.getFor(CameraComponent::class.java)
    bacM = ComponentMapper.getFor(BackgroundComponent::class.java)
    
    shapeRenderer = ShapeRenderer()
    shapeRenderer.setAutoShapeType(true)
    
    b2d = Box2DDebugRenderer()
    
    cameras = Array()
    
    renderQueue = Array()
    
    comparator = Comparator { a, b -> Math.signum(traM.get(b).pos.z - traM.get(a).pos.z).toInt() }
  }
  
  constructor(camera: OrthographicCamera, batch: SpriteBatch, world: World) : super(Family.all(TransformComponent::class.java, TextureComponent::class.java).get()) {
    this.cam = camera
    this.batch = batch
    this.world = world
  }
  
  override fun update(deltaTime: Float) {
    super.update(deltaTime)
    
    if (cameras.size == 0) throw IllegalStateException("At least 1 Camera must exist")
    renderQueue.sort(comparator)
    
    if (cameras.size == 1) {
      cam.position.set(cameras[0].position)
      cam.zoom = cameras[0].zoom
      cam.update()
      batch.projectionMatrix = cam.combined
      Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
      draw(false)
    } else if (cameras.size == 2) {
      Gdx.gl.glViewport(0, 0, Gdx.graphics.width / 2, Gdx.graphics.height)
      cam.position.set(cameras[0].position)
      cam.zoom = cameras[0].zoom
      cam.update()
      batch.projectionMatrix = cam.combined
      draw(true)
      
      shapeRenderer.projectionMatrix = cam.combined
      shapeRenderer.begin()
      
      val topRight = cam.unproject(Vector3(Gdx.graphics.width.toFloat() - 1f, 0f, 0f))
      shapeRenderer.line(topRight.x, topRight.y, topRight.x, topRight.y - 9f)
      
      shapeRenderer.end()
      
      Gdx.gl.glViewport(Gdx.graphics.width / 2, 0, Gdx.graphics.width / 2, Gdx.graphics.height)
      cam.position.set(cameras[1].position)
      cam.zoom = cameras[1].zoom
      cam.update()
      batch.projectionMatrix = cam.combined
      draw(true)
    } else {
      throw IllegalStateException("Can't have more than 2 cameras")
    }
    cameras.clear()
    renderQueue.clear()
  }
  
  private fun draw(splitScreen: Boolean) {
    batch.begin()
    
    val start = cam.unproject(Vector3(0f, Gdx.graphics.height.toFloat(), 0f))
    val end = cam.unproject(Vector3(Gdx.graphics.width.toFloat(), 0f, 0f))
    val size = (com.badlogic.gdx.math.Rectangle(start.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y)))
    
    for (entity: Entity in renderQueue) {
      
      val trans = traM.get(entity)
      val texture = texM.get(entity).texture
      
      if (bacM.has(entity)) {
        val b = bacM.get(entity)
        
        val x = if (b.parallax) start.x / b.parallaxFactor else start.x
        val y = if (b.parallax) start.y / b.parallaxFactor else start.y
        
        trans.pos.set(x, y, 10.0f)
        
        val width = texture.texture.width.toFloat()
        val height = texture.texture.height.toFloat()
        
        val scale = 10
        
        batch.draw(texture.texture
            , size.x, size.y
            , width / Constants.PPM * scale, height / Constants.PPM * scale
            , (trans.pos.x * Constants.PPM).toInt(), -(trans.pos.y * Constants.PPM).toInt()
            , texture.texture.width * scale, texture.texture.height * scale
            , false, false)
      } else {
        
        val width = texture.regionWidth.toFloat()
        val height = texture.regionHeight.toFloat()
        val originX = width * 0.5f
        val originY = height * 0.5f
        
        batch.draw(texture
            , trans.x - originX, trans.y - originY
            , originX, originY
            , width, height
            , trans.scale.x * PIXELS_TO_METRES, trans.scale.y * PIXELS_TO_METRES
            , trans.rotation)
      }
    }
    
    batch.end()
    
    if (Gdx.app.logLevel >= Logger.DEBUG) b2d.render(world, cam.combined)
  }
  
  override fun processEntity(entity: Entity?, deltaTime: Float) {
    val camera: CameraComponent? = camM.get(entity)
    if (camera != null) {
      cameras.add(camera)
    }
    renderQueue.add(entity)
  }
}
