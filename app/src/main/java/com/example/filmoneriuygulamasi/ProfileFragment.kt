import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmoneriuygulamasi.R
import com.example.filmoneriuygulamasi.databinding.FragmentProfileBinding
import com.example.filmoneriuygulamasi.databinding.DialogEditUsernameBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "UserPreferences"
    private val KEY_USER_NAME = "userName"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPreferences'ı başlat
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Kullanıcı adını SharedPreferences'tan oku ve göster
        val savedUserName = sharedPreferences.getString(KEY_USER_NAME, "Kullanıcı Adı")
        binding.userName.text = savedUserName

        // Kullanıcı adını düzenleme ikonuna tıklanma olayını dinle
        binding.editUserNameIcon.setOnClickListener {
            showEditUserNameDialog()
        }
    }

    private fun showEditUserNameDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_username, null)
        val binding = DialogEditUsernameBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Kullanıcı Adı Düzenle")
            .setView(dialogView)
            .setNegativeButton("İptal", null)
            .setPositiveButton("Kaydet") { _, _ ->
                val newUserName = binding.userNameEditText.text.toString()
                if (newUserName.isNotBlank()) {
                    updateUserName(newUserName)
                }
            }
            .create()

        dialog.show()
    }

    private fun updateUserName(newUserName: String) {
        binding.userName.text = newUserName
        // Kullanıcı adını SharedPreferences'ta kaydet
        with(sharedPreferences.edit()) {
            putString(KEY_USER_NAME, newUserName)
            apply() // veya commit() kullanabilirsiniz
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
