package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.math3.stat.regression.SimpleRegression;

class Test {
	public static void main(String[] args) {
		try {
			File csv = new File("C:\\Users\\levelfive\\Documents\\coffe_and_lunch.csv");// CSVデータの読み込み

			SimpleRegression coffee = new SimpleRegression();// インスタンスを作る
			SimpleRegression lunch = new SimpleRegression();

			FileReader fr = new FileReader(csv);

			BufferedReader br = new BufferedReader(fr);// csvというファイルオブジェクトを読むfileReaderの中身をメモリー空間(Buffered)にとどめておく
			String line = "";// 変数lineの初期化
			while ((line = br.readLine()) != null) {// csvの表の一行を読み取り、要素があるとき実行

				// 一行をデータの要素に分割
				String[] array = line.split(",");// ","を区切り文字として認識し、区切った要素を配列に入れる
				for (int i = 0; i < array.length; i++) {
					if (i == 0)// index[0]はアルファベットなので除外する
					{
						continue;// 最初のループ判定へ
					} else if (i == 1) {
						double num = Double.parseDouble(array[i]);// array[i]の要素をdouble型にキャストし、double型のnumに代入
						double num2 = Double.parseDouble(array[3]);// array[3]の要素をdouble型にキャストし、double型のnum2に代入
						coffee.addData(num, num2);// numとnum2をregressionに加える
					} else if (i == 2) {
						double num3 = Double.parseDouble(array[i]);// array[i]の要素をdouble型にキャストし、double型のnumに代入
						double num4 = Double.parseDouble(array[3]);// array[3]の要素をdouble型にキャストし、double型のnum2に代入
						lunch.addData(num3, num4);// num3とnum4をregressionに加える
					} else if (i == 3) {
						continue;// 最初のループ判定へ
					}
				}
			}

			double intercept_coffee = coffee.getIntercept();// Intercept(切片)を貰ってintercept_coffeeに入れる
			double slop_coffee = coffee.getSlope();// Slope(傾き)を貰ってslop_coffeに入れる
			double intercept_lunch = lunch.getIntercept();// Intercept(切片)を貰ってintercept_lunch に入れる
			double slop_lunch = lunch.getSlope();// Slope(傾き)を貰ってslop_lunchに入れる

			int tasuuketu = 0;//コーヒーのほうが誤差が少ない判定されるとインクリメントされる変数

			while ((line = br.readLine()) != null) {// csvの表の一行を読み取り、要素があるとき実行
				// 一行をデータの要素に分割
				String[] array2 = line.split(",");// ","を区切り文字として認識し、区切った要素を配列に入れる
				for (int j = 0; j < array2.length; j++) {
					double coffeegosa = 0;//誤差の判定に使われる変数を初期化(コーヒー用)
					double lunchgosa = 0;//誤差の判定に使われる変数を初期化(ランチ用)
					if (j == 0) {// index[0]の時だけ作業する
						double num5 = Double.parseDouble(array2[1]);//コーヒーの数値をdouble型に
						double num6 = Double.parseDouble(array2[2]);//ランチの数値をdouble型に
						double num7 = Double.parseDouble(array2[3]);//コードの数値をdouble型に
						double coffeecord = intercept_coffee * num5 + slop_coffee;//推定値を求める
						double lunchcord = intercept_lunch * num6 + slop_lunch;//推定値を求める
						coffeegosa = num7 - coffeecord;//実際のコードの数値と推定値の誤差(コーヒー)
						lunchgosa = num7 - lunchcord;//実際のコードの数値と推定値の誤差(ランチ)
						 if(Math.abs(coffeegosa)<Math.abs(lunchgosa)){//誤差を比べて、コーヒーの誤差が小さい
							 tasuuketu++;//tasuuketuをインクリメント
						 }else if(Math.abs(coffeegosa)>Math.abs(lunchgosa)){//誤差を比べて、コーヒーの誤差が大きい
							 tasuuketu--;//tasuuketuをデクリメント
						 }
						 }
			}
			}
			if(tasuuketu > 0) {
				System.out.println("コーヒー");
				}else if(tasuuketu < 0){
					System.out.println("ランチ");
				}else{
					System.out.println("変わらんよ");
				}
			br.close();// スレッドの終了
		} catch (FileNotFoundException e) {
			// File オブジェクト生成時の例外捕捉
			e.printStackTrace();
		} catch (IOException e) {
			// BufferedReaderオブジェクト生成時の例外捕捉
			e.printStackTrace();
		}

	}
}