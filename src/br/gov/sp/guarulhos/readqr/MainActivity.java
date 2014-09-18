package br.gov.sp.guarulhos.readqr;

import jim.h.common.android.lib.zxing.config.ZXingLibConfig;
import jim.h.common.android.lib.zxing.integrator.IntentIntegrator;
import jim.h.common.android.lib.zxing.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Handler handler = new Handler();
	private TextView txtScanResult;
	private ZXingLibConfig zxingLibConfig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtScanResult = (TextView) findViewById(R.id.scan_result);
		zxingLibConfig = new ZXingLibConfig();
		zxingLibConfig.useFrontLight = true;
		
		

	}

	public void scanear(View v) {
		IntentIntegrator.initiateScan(MainActivity.this, zxingLibConfig);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE: 
			IntentResult scanResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, data);
			if (scanResult == null) {
				return;
			}
			//Tratamento da tag pmg e exibição na tela.
			final String result = scanResult.getContents().substring(0, 4).equalsIgnoreCase("pmg:")?scanResult.getContents().substring(4, scanResult.getContents().length()):"QR CODE FORA DO PADRÃO";
			if (result != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						txtScanResult.setText(result);
					}
				});
			}
			break;
		default:
		}
	}
}
