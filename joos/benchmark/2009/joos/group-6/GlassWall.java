public class GlassWall extends Wall {
	public GlassWall() {
		super();
		this.setAngleFactor(0);
	}

	// @Override
	public Position reflectBall(int angle, Ball ball) {
		return new Position(-1,-1);
	}
}
