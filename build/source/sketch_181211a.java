import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_181211a extends PApplet {

int NUM = 1000; // パーティクルの数
// パーティクルを格納する配列
ParticleVec3[] particles = new ParticleVec3[NUM];

public void setup() {
  
  frameRate(60);
  // パーティクルを初期化する
  for (int i = 0; i < NUM; i++) {
    // クラスをインスタンス化する
    particles[i] = new ParticleVec3();
    // 初期位置はランダムにする
    particles[i].location.set(random(width), random(height), random(height/2));
    particles[i].mass = random(1, 2);
    particles[i].radius = 0.5f;
    particles[i].friction = 0.02f;
  }
  noStroke();
  background(0);
}

public void draw() {
  fill(0, 15);
  rect(0, 0, width, height);
  fill(255);
  // パーティクル同士の引き付けあう力を計算する
  for (int i = 0; i < NUM; i++) {
    for (int j = 0; j < i; j++) {
      // パーティクル同士の距離と質量から引力を計算する
      particles[i].attract(particles[j].location, particles[j].mass, 2.0f, 800.0f);
    }
    particles[i].update();
    // 壁を突き抜けたら反対になる
    particles[i].throughWalls();
    particles[i].draw();
  }
}

// int ANUM = 20; // アトラクターの数
// int NUM = 2000; // パーティクルの数
// // アトラクターを格納する配列
// ParticleVec3[] attractors = new ParticleVec3[ANUM];
// // パーティクルを格納する配列
// ParticleVec3[] particles = new ParticleVec3[NUM];
//
// void setup() {
//   size(800, 600, P3D);
//   frameRate(60);
//   // アトラクターを初期化する
//   for (int i = 0; i < ANUM; i++) {
//     attractors[i] = new ParticleVec3();
//     attractors[i].location.set(random(width), random(height), random(height/2));
//     attractors[i].radius = 5.0;
//   }
//   // パーティクルを初期化する
//   for (int i = 0; i < NUM; i++) {
//     particles[i] = new ParticleVec3();
//     particles[i].location.set(random(width), random(height), random(height/2));
//     particles[i].friction = 0.001;
//     particles[i].radius = 1.0;
//     particles[i].mass = random(2.0);
//   }
//   background(0);
// }
//
// void draw() {
//   fill(0, 15);
//   rect(0, 0, width, height);
//   noStroke();
//   // アトラクターの数だけ更新する
//   for (int j = 0; j < ANUM; j++) {
//     // アトラクターを描画する
//     fill(255, 0, 0);
//     attractors[j].draw();
//     for (int i = 0; i < NUM; i++) {
//       // アトラクターの場所への吸引力を設定する
//       particles[i].attract(attractors[j].location, 50, 10, 800);
//     }
//   }
//   // パーティクルの位置を更新して描画する
//   for (int i = 0; i < NUM; i++) {
//     fill(255);
//     particles[i].update();
//     particles[i].draw();
//     particles[i].throughWalls();
//   }
// }

// int NUM = 2000; // パーティクルの数
// // パーティクルを格納する配列
// ParticleVec2[] particles = new ParticleVec2[NUM];
//
// void setup() {
//   size(800, 600, P2D);
//   frameRate(60);
//   // パーティクルを初期化する
//   for (int i = 0; i < NUM; i++) {
//     // クラスをインスタンス化する
//     particles[i] = new ParticleVec2();
//     // 初期位置は画面の中心にする
//     particles[i].location.set(random(width), random(height));
//     // 重力を0.0にする
//     particles[i].gravity.set(0.0, 0.0);
//     // 摩擦力を0.01にする
//     particles[i].friction = 0.01;
//   }
// }
//
// void draw() {
//   //背景をフェードさせる
//   fill(0, 31);
//   rect(0, 0, width, height);
//   fill(255);
//   noStroke();
//   fill(255);
//   // パーティクルの位置を更新して描画する
//   for (int i = 0; i < NUM; i++) {
//     particles[i].update(); // 位置を更新する
//     particles[i].draw(); // 描画する
//     particles[i].bounceOffWalls(); // 壁でバウンドさせる
//   }
// }
//
// // マウスドラッグで吸引力を発生させる
// void mouseDragged() {
//   // パーティクルの数だけ繰り返し
//   for (int i = 0; i < NUM; i++) {
//     PVector mouseLoc = new PVector(mouseX, mouseY);
//     particles[i].attract(mouseLoc, 200, 5, 20);
//   }
// }
//ParticleVec3クラス
//物体の運動を計算(運動方程式)
class ParticleVec3 {
  PVector location; //位置
  PVector velocity; //速度
  PVector acceleration; //加速度
  PVector gravity; //重力
  float mass; //質量
  float friction; //摩擦力
  PVector min; //稼動範囲 min
  PVector max; //稼動範囲 max
  float radius; //パーティクル半径
  float G; //重力定数

  //コンストラクター
  ParticleVec3() {
    radius = 4.0f;
    mass = 0.5f; //質量は 0.5 に設定
    friction = 0.0f; //摩擦力を0.01に設定
    G = 1.0f; //重力定数を1.0に
    //位置、速度、加速度を初期化
    location = new PVector(0.0f, 0.0f, 0.0f);
    velocity = new PVector(0.0f, 0.0f, 0.0f);
    acceleration = new PVector(0.0f, 0.0f, 0.0f);
    //重力なし
    gravity = new PVector(0.0f, 0.0f, 0.0f);
    //稼動範囲を設定
    min = new PVector(0, 0, -width);
    max = new PVector(width, height, width);
  }

  //運動方程式から位置を更新
  public void update() {
    //重力を加える
    acceleration.add(gravity);
    //加速度から速度を算出
    velocity.add(acceleration);
    //摩擦力から速度を変化
    velocity.mult(1.0f - friction);
    //速度から位置を算出
    location.add(velocity);
    //加速度を0にリセット(等速運動)
    acceleration.set(0, 0, 0);
  }

  //描画
  public void draw() {
    pushMatrix();
    translate(location.x, location.y, location.z);
    ellipse(0, 0, mass * radius * 2, mass * radius * 2);
    popMatrix();
  }

  //力を加える
  public void addForce(PVector force) {
    force.div(mass); //力と質量から加速度を算出
    acceleration.add(force); //力を加速度に加える
  }

  //引力を計算
  public void attract(PVector center, float _mass, float min, float max) {
    //距離を算出
    float distance = PVector.dist(center, location);
    //距離を指定した範囲内に納める(極端な値を無視する)
    distance = constrain(distance, min, max);
    //引力の強さを算出 F = G(Mm/r^2)
    float strength = G * (mass * _mass) / (distance * distance);
    //引力の中心点とパーティクル間のベクトルを作成
    PVector force = PVector.sub(center, location);
    //ベクトルを正規化
    force.normalize();
    //ベクトルに力の強さを乗算
    force.mult(strength);
    //力を加える
    addForce(force);
  }

  //パーティクル同士の引力を計算
  public void attractParticle(ParticleVec3 particle, float min, float max) {
    //距離を算出
    float distance = PVector.dist(particle.location, location);
    //距離を指定した範囲内に納める(極端な値を無視する)
    distance = constrain(distance, min, max);
    //引力の強さを算出 F = G(Mm/r^2)
    float strength = G * (mass * particle.mass) / (distance * distance);
    //引力の中心点とパーティクル間のベクトルを作成
    PVector force = PVector.sub(particle.location, location);
    //ベクトルを正規化
    force.normalize();
    //ベクトルに力の強さを乗算
    force.mult(strength);
    //力を加える
    addForce(force);
  }

  //壁でバウンドさせる
  public void bounceOffWalls() {
    if (location.x > max.x) {
      location.x = max.x;
      velocity.x *= -1;
    }
    if (location.x < min.x) {
      location.x = min.x;
      velocity.x *= -1;
    }
    if (location.y > max.y) {
      location.y = max.y;
      velocity.y *= -1;
    }
    if (location.y < min.y) {
      location.y = min.y;
      velocity.y *= -1;
    }
    if (location.z > max.z) {
      location.z = max.z;
      velocity.z *= -1;
    }
    if (location.z < min.z) {
      location.z = min.z;
      velocity.z *= -1;
    }
  }

  //壁を突き抜けて反対から出現させる
  public void throughWalls() {
    if (location.x < min.x) {
      location.x = max.x;
    }
    if (location.y < min.y) {
      location.y = max.y;
    }
    if (location.z < min.z) {
      location.z = max.z;
    }
    if (location.x > max.x) {
      location.x = min.x;
    }
    if (location.y > max.y) {
      location.y = min.y;
    }
    if (location.z > max.z) {
      location.z = min.z;
    }
  }
}


// //ParticleVec2クラス
// //物体の運動を計算(運動方程式)
// class ParticleVec2 {
//   PVector location; //位置
//   PVector velocity; //速度
//   PVector acceleration; //加速度
//   PVector gravity; //重力
//   float mass; //質量
//   float friction; //摩擦力
//   PVector min; //稼動範囲 min
//   PVector max; //稼動範囲 max
//   float radius; //パーティクル半径
//   float G; //重力定数
//
//   //コンストラクター
//   ParticleVec2() {
//     radius = 4.0;
//     mass = 1.0; //質量は 1.0 に設定
//     friction = 0.01; //摩擦力を0.01に設定
//     G = 1.0; //重力定数を1.0に
//     //位置、速度、加速度を初期化
//     location = new PVector(0.0, 0.0);
//     velocity = new PVector(0.0, 0.0);
//     acceleration = new PVector(0.0, 0.0);
//     //重力なし
//     gravity = new PVector(0.0, 0.0);
//     //稼動範囲を設定
//     min = new PVector(0.0, 0.0);
//     max = new PVector(width, height);
//   }
//
//   //運動方程式から位置を更新
//   void update() {
//     //重力を加える
//     acceleration.add(gravity);
//     //加速度から速度を算出
//     velocity.add(acceleration);
//     //摩擦力から速度を変化
//     velocity.mult(1.0 - friction);
//     //速度から位置を算出
//     location.add(velocity);
//     //加速度を0にリセット(等速運動)
//     acceleration.set(0, 0);
//   }
//
//   //描画
//   void draw() {
//     ellipse(location.x, location.y, mass * radius * 2, mass * radius * 2);
//   }
//
//   //壁でバウンドさせる
//   void bounceOffWalls() {
//     if (location.x > max.x) {
//       location.x = max.x;
//       velocity.x *= -1;
//     }
//     if (location.x < min.x) {
//       location.x = min.x;
//       velocity.x *= -1;
//     }
//     if (location.y > max.y) {
//       location.y = max.y;
//       velocity.y *= -1;
//     }
//     if (location.y < min.y) {
//       location.y = min.y;
//       velocity.y *= -1;
//     }
//   }
//
//   //壁を突き抜けて反対から出現させる
//   void throughWalls() {
//     if (location.x < min.x) {
//       location.x = max.x;
//     }
//     if (location.y < min.y) {
//       location.y = max.y;
//     }
//     if (location.x > max.x) {
//       location.x = min.x;
//     }
//     if (location.y > max.y) {
//       location.y = min.y;
//     }
//   }
//
//   //力を加える
//   void addForce(PVector force) {
//     force.div(mass); //力と質量から加速度を算出
//     acceleration.add(force); //力を加速度に加える
//   }
//
//   //引力を計算
//   void attract(PVector center, float _mass, float min, float max) {
//     //距離を算出
//     float distance = PVector.dist(center, location);
//     //距離を指定した範囲内に納める(極端な値を無視する)
//     distance = constrain(distance, min, max);
//     //引力の強さを算出 F = G(Mm/r^2)
//     float strength = G * (mass * _mass) / (distance * distance);
//     //引力の中心点とパーティクル間のベクトルを作成
//     PVector force = PVector.sub(center, location);
//     //ベクトルを正規化
//     force.normalize();
//     //ベクトルに力の強さを乗算
//     force.mult(strength);
//     //力を加える
//     addForce(force);
//   }
// }
  public void settings() {  size(800, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_181211a" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
