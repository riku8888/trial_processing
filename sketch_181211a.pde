int NUM = 1000; // パーティクルの数
// パーティクルを格納する配列
ParticleVec3[] particles = new ParticleVec3[NUM];

void setup() {
  size(800, 600, P3D);
  frameRate(60);
  // パーティクルを初期化する
  for (int i = 0; i < NUM; i++) {
    // クラスをインスタンス化する
    particles[i] = new ParticleVec3();
    // 初期位置はランダムにする
    particles[i].location.set(random(width), random(height), random(height/2));
    particles[i].mass = random(1, 2);
    particles[i].radius = 0.5;
    particles[i].friction = 0.02;
  }
  noStroke();
  background(0);
}

void draw() {
  fill(0, 15);
  rect(0, 0, width, height);
  fill(255);
  // パーティクル同士の引き付けあう力を計算する
  for (int i = 0; i < NUM; i++) {
    for (int j = 0; j < i; j++) {
      // パーティクル同士の距離と質量から引力を計算する
      particles[i].attract(particles[j].location, particles[j].mass, 2.0, 800.0);
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
