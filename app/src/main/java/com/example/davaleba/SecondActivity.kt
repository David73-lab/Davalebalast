import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class SecondActivity : AppCompatActivity() {

    private lateinit var link : EditText
    private lateinit var name : EditText
    private lateinit var butt2 : Button
    private lateinit var img : ImageView
    private lateinit var text : TextView
    private lateinit var auth : FirebaseAuth
    private lateinit var db : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        link = findViewById(R.id.plainTextLink)
        name = findViewById(R.id.plainTextName)
        butt2 = findViewById(R.id.butt2)
        img = findViewById(R.id.image1)
        text = findViewById(R.id.textView1)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        registerListeners()
        setValueEventListener()

    }

    private fun registerListeners() {

        butt2.setOnClickListener {

            val nametext = name.text.toString()
            val linktext = link.text.toString()

            val user = UserInfo(nametext,linktext)

            auth.currentUser?.uid?.let {uid ->
                db.child(uid).setValue(user)
            }

        }

    }

    private fun setValueEventListener() {
        auth.currentUser?.uid?.let {uid ->
            db.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userinfo = snapshot.getValue(UserInfo::class.java)?:return
                    text.text = userinfo.name.toString()

                    Glide.with(this@SecondActivity)
                        .load(userinfo.link)
                        .into(img);

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}