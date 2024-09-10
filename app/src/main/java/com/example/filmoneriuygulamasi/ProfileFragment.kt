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
import com.example.filmoneriuygulamasi.databinding.DialogEditProfileBinding
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
    private val KEY_TARGET = "target"

    private val targets = listOf(50, 150, 300, 600, Int.MAX_VALUE) // Hedef aşamaları

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
        val moviesWatched = sharedPreferences.getInt(KEY_MOVIES_WATCHED, 0)
        val moviesToWatch = sharedPreferences.getInt(KEY_MOVIES_TO_WATCH, 50)
        updateMovieStats(moviesWatched, moviesToWatch)

        // Kullanıcı adını düzenleme ikonuna tıklanma olayını dinle
        binding.editUserNameIcon.setOnClickListener {
            showEditUserNameDialog()
        }

        binding.editprofileImageIcon.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun showEditProfileDialog() {
        val dialogBinding = DialogEditProfileBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Profil Seç")
            .setView(dialogBinding.root)
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

        dialogBinding.imageViewCloseEditProfileDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.imageMale.setOnClickListener {
            updateProfileImage("male")
            saveProfilePreference("male")
            dialog.dismiss()
        }

        dialogBinding.imageFemale.setOnClickListener {
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
        val dialogBinding = DialogEditUsernameBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Kullanıcı Adı Düzenle")
            .setView(dialogBinding.root)
            .setNegativeButton("İptal", null)
            .setPositiveButton("Kaydet") { _, _ ->
                val newUserName = dialogBinding.userNameEditText.text.toString()
                if (newUserName.isNotBlank()) {
                    updateUserName(newUserName)
                }
            }
            .create()

        dialogBinding.imageViewCloseUserNameDialog.setOnClickListener {
            dialog.dismiss()
        }

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
