import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.luckyfanapp.GameActivity
import com.example.luckyfanapp.MainActivity
import com.example.luckyfanapp.R

class GameOverFragment : Fragment() {

    private lateinit var restartButton: ImageView
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        restartButton = view.findViewById(R.id.restartButtonGameOver)
        backButton = view.findViewById(R.id.backButtonGameOver)

        restartButton.setOnClickListener {
            val intent = Intent(requireContext(), GameActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
