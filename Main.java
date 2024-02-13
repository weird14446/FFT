import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int cnt = Integer.parseInt(br.readLine());
        Complex[] input = new Complex[cnt];
        for (int i = 0; i < input.length; i++) {
            String[] in = br.readLine().split(" ");
            input[i] = new Complex(Double.parseDouble(in[0]), Double.parseDouble(in[1]));
        }

        Complex[] output1 = dft(input);

        for (int i = 0; i < input.length; i++)
            bw.write(output1[i] + "\n");

        fft(input);
        bw.write("\n");
        for (int i = 0; i < input.length; i++)
            bw.write(input[i] + "\n");

        br.close();
        bw.close();
    }

    static Complex[] dft(Complex[] input) {
        Complex[] output = new Complex[input.length];

        for (int i = 0; i < output.length; i++) {
            double real = 0;
            double img = 0;
            for (int n = 0; n < input.length; n++) {
                double angle = 2 * Math.PI * n * i / input.length;
                real += input[n].re * Math.cos(angle) + input[n].im * Math.sin(angle);
                img += -input[n].re * Math.sin(angle) + input[n].im * Math.cos(angle);
            }
            output[i] = new Complex(real, img);
        }

        return output;
    }

    public static void fft(Complex[] input) {
        final int size = input.length;
        if (size <= 1)
            return;

        final Complex[] even = new Complex[size / 2];
        final Complex[] odd = new Complex[size / 2];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                even[i / 2] = input[i];
            } else {
                odd[(i - 1) / 2] = input[i];
            }
        }
        fft(even);
        fft(odd);
        for (int k = 0; k < size / 2; k++) {
            Complex t = Complex.polar(1.0, -2 * Math.PI * k / size).times(odd[k]);
            input[k] = even[k].plus(t);
            input[k + size / 2] = even[k].minus(t);
        }
    }

    public static void ifft(Complex[] input)
    {
        int N = input.length;

        for (int i = 0; i < N; i++)
            input[i] = input[i].conjugate();

        fft(input);

        for (int i = 0; i < N; i++)
            input[i] = input[i].conjugate();

        for (int i = 0; i < N; i++)
            input[i] = input[i].times(1.0 / N);
    }

}
