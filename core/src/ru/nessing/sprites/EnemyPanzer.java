package ru.nessing.sprites;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.nessing.base.Ship;
import ru.nessing.math.Rect;
import ru.nessing.pool.BulletPool;
import ru.nessing.pool.ExplosionPool;

public class EnemyPanzer extends Ship {

    private boolean checkDirectY = false;

    private final Vector2 speedNormal = new Vector2();

    public void setCheckDirectY(boolean checkDirectY) {
        this.checkDirectY = checkDirectY;
    }

    public EnemyPanzer(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound soundShoot) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.soundShoot = soundShoot;
        bulletSpeed = new Vector2();
        positionBullet = new Vector2();
        this.direction = new Vector2();
        this.volumeShoot = 0.2f;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        positionBullet.set(pos.x - 0.1f, pos.y + 0.04f);
        if (pos.x + getHeight() < worldBounds.getHalfWidth()) {
            if (!checkDirectY) {
                this.direction.set(speedNormal);
                checkDirectY = true;
            }
        } else {
            reloadTimer = reloadInterval * 0.9f;
        }
        direction.y = 0;

        if (getLeft() < worldBounds.getLeft() - getWidth()) {
            checkDirectY = false;
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 startSpeed,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletSpeed,
            int damage,
            int hp,
            float reloadInterval,
            float height
    ) {
        this.regions = regions;
        this.speedNormal.set(startSpeed.x, 0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed = bulletSpeed;
        this.bulletSpeed.y = 0.2f;
        this.damage = damage;
        this.hp = hp;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.direction.set(-0.5f, 0);
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < pos.x
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < getBottom());
    }

}
