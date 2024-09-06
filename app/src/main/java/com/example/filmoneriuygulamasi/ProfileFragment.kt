import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    private val KEY_PROFILE = "profile"

    private val KEY_MOVIES_WATCHED = "moviesWatched"
    private val KEY_MOVIES_TO_WATCH = "moviesToWatch"

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

        val savedProfile = sharedPreferences.getString(KEY_PROFILE, "default")
        updateProfileImage(savedProfile ?: "default")

        // İzlenen ve izlenecek filmler sayısını oku ve göster
        val moviesWatched = sharedPreferences.getInt(KEY_MOVIES_WATCHED, 56)
        val moviesToWatch = sharedPreferences.getInt(KEY_MOVIES_TO_WATCH, 96)
        updateMovieStats(moviesWatched, moviesToWatch)


        // Kullanıcı adını düzenleme ikonuna tıklanma olayını dinle
        binding.editUserNameIcon.setOnClickListener {
            showEditUserNameDialog()
        }

        binding.editprofileImageIcon.setOnClickListener{
            showEditProfileDialog()

        }
    }


    private fun showEditProfileDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_profile, null)
        val imageMale = dialogView.findViewById<ImageView>(R.id.imageMale)
        val imageFemale = dialogView.findViewById<ImageView>(R.id.imageFemale)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Profil Seç")
            .setView(dialogView)
            .setPositiveButton("Kaydet") { _, _ ->
                // "Kaydet" butonuna tıklanınca mevcut profil tercihini kaydet
                val currentProfile = sharedPreferences.getString(KEY_PROFILE, "default")
                updateProfileImage(currentProfile ?: "default")
            }
            .setNegativeButton("Sıfırla") { dialog, _ ->
                updateProfileImage("default")
                saveProfilePreference("default")
                dialog.dismiss()
            }
            .create()

        imageMale.setOnClickListener {
            updateProfileImage("male")
            saveProfilePreference("male")
            dialog.dismiss()
        }

        imageFemale.setOnClickListener {
            updateProfileImage("female")
            saveProfilePreference("female")
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun updateProfileImage(profile: String) {
        val profileImageRes = when (profile) {
            "male" -> R.drawable.man
            "female" -> R.drawable.female
            else -> R.drawable.baseline_person_24
        }
        binding.profileImage.setImageResource(profileImageRes)
    }


    private fun saveProfilePreference(profile: String) {
        // Cinsiyet tercihini SharedPreferences'ta kaydet
        with(sharedPreferences.edit()) {
            putString(KEY_PROFILE, profile)
            apply()
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

    private fun updateMovieStats(moviesWatched: Int, moviesToWatch: Int) {
        binding.moviesWatched.text = "İzlenen Filmler: $moviesWatched"
        binding.moviesToWatch.text = "İzlenecek Filmler: $moviesToWatch"

        val progress = if (moviesToWatch == 0) 0 else (moviesWatched * 100) / moviesToWatch
        binding.progressBar.progress = progress
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
