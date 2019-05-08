package calculadora.dazz.com.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class MainActivity extends AppCompatActivity {
    Double result ;
    private Scriptable scope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button calcular = findViewById(R.id.calcular);
        final TextView ecuacion = findViewById(R.id.ecuacion);
        final TextView resultado = findViewById(R.id.resultado);
        final Context  rhino  = Context.enter();
        rhino.setOptimizationLevel(-1);

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Object[] fuctionParams = new Object[]{ecuacion.getText().toString()};
                resultado.setText("texto:" + ecuacion.getText().toString());
                try{
                    Scriptable scope = rhino.initStandardObjects();
                    rhino.evaluateString(scope,"function Calculer (formule){ return eval(formule)     ;}","JavaScript",1,null);
                    Function function = (Function) scope.get("Calculer", scope);
                    result = (Double) function.call(rhino,scope,scope,fuctionParams);
                    resultado.setText("" + result);
                }catch (RhinoException e){
                    e.printStackTrace();
                    resultado.setText("SOLO OPERACIONES MATEMATICAS");
                }finally {

                }
            }
        });
    }
}
