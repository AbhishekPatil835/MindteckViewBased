package com.android.mindteckapp

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter
    private lateinit var searchView: SearchView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val fruits = listOf(
        "apple" to "A red fruit",
        "banana" to "A yellow fruit",
        "orange" to "A citrus fruit",
        "blueberry" to "Small round fruit",
        "strawberry" to "Red, juicy fruit",
        "grape" to "A bunch of small, sweet fruit",
        "kiwi" to "A brown, hairy fruit",
        "mango" to "A tropical, sweet fruit",
        "pineapple" to "A tropical fruit with spiky leaves",
        "watermelon" to "A large, juicy fruit"
    )

    private val nature = listOf(
        "mountain" to "A large elevated landform",
        "river" to "A large flowing body of water",
        "forest" to "A dense area filled with trees",
        "desert" to "A dry, barren land",
        "ocean" to "A vast body of salt water",
        "lake" to "A body of water surrounded by land",
        "waterfall" to "A cascade of water falling from a height",
        "volcano" to "A mountain with a crater that can erupt",
        "canyon" to "A deep gorge, typically with a river flowing through it",
        "savanna" to "A grassy plain with few trees"
    )

    private val transportation = listOf(
        "car" to "A vehicle powered by an engine",
        "bicycle" to "A two-wheeled vehicle powered by pedaling",
        "airplane" to "A powered flying vehicle",
        "boat" to "A vessel designed to float on water",
        "train" to "A series of connected railway cars",
        "bus" to "A large motor vehicle for passengers",
        "helicopter" to "A type of aircraft with rotating blades",
        "scooter" to "A small two-wheeled vehicle",
        "submarine" to "A watercraft capable of underwater operation",
        "tram" to "A rail vehicle operating on streets"
    )

    private val animals = listOf(
        "dog" to "A domesticated carnivorous mammal",
        "cat" to "A small domesticated carnivorous mammal",
        "elephant" to "A large herbivorous mammal with a trunk",
        "tiger" to "A large carnivorous feline",
        "bird" to "A warm-blooded egg-laying vertebrate",
        "lion" to "A large carnivorous feline known as the king of the jungle",
        "giraffe" to "A tall herbivorous mammal with a long neck",
        "panda" to "A large bear-like mammal native to China",
        "zebra" to "A wild horse-like animal with black-and-white stripes",
        "koala" to "A small marsupial native to Australia"
    )

    private val instruments = listOf(
        "guitar" to "A stringed musical instrument",
        "piano" to "A large keyboard musical instrument",
        "drums" to "A percussion instrument",
        "violin" to "A stringed instrument played with a bow",
        "flute" to "A wind instrument",
        "trumpet" to "A brass wind instrument",
        "saxophone" to "A wind instrument made of brass",
        "clarinet" to "A woodwind instrument",
        "keyboard" to "A musical instrument with keys",
        "cello" to "A stringed instrument played while seated"
    )

    private val technology = listOf(
        "computer" to "An electronic device for processing data",
        "phone" to "A mobile device for communication",
        "television" to "An electronic device for watching programs",
        "camera" to "A device used to take photographs or videos",
        "radio" to "A device for receiving audio signals",
        "tablet" to "A portable touchscreen device",
        "laptop" to "A portable personal computer",
        "smartwatch" to "A wearable computing device",
        "headphones" to "A device worn over the ears to listen to audio",
        "speaker" to "A device that produces sound"
    )

    private val images = getImages()
    private var currentViewPagerPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        floatingActionButton = findViewById(R.id.floatingActionButton)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        setupRecyclerView()
        setupViewPager()
        setupSearchView()
        setupFloatingActionButton()
    }

    private fun getFilteredItemsForCurrentPosition(): List<Pair<Pair<String, String>, Int>> {
        val filteredItems = when (currentViewPagerPosition) {
            0 -> fruits
            1 -> nature
            2 -> transportation
            3 -> animals
            4 -> instruments
            5 -> technology
            else -> emptyList()
        }
        val pairedItems = filteredItems.map {
            it to images[currentViewPagerPosition]
        }
        return pairedItems
    }

    private fun setupRecyclerView() {
        adapter = ListAdapter(getFilteredItemsForCurrentPosition())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewPager() {
        val imageAdapter = ImageAdapter(getImages())
        viewPager.adapter = imageAdapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentViewPagerPosition = position
                updateRecyclerViewBasedOnViewPager()
            }
        })
    }

    private fun updateRecyclerViewBasedOnViewPager() {
        val filteredItems = when (currentViewPagerPosition) {
            0 -> fruits
            1 -> nature
            2 -> transportation
            3 -> animals
            4 -> instruments
            5 -> technology
            else -> emptyList()
        }

        val pairedItems = filteredItems.map {
            it to images[currentViewPagerPosition]
        }

        adapter.updateList(pairedItems)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    updateRecyclerViewBasedOnViewPager()
                } else {
                    val filteredList = adapter.getList().filter {
                        it.first.first.contains(newText, ignoreCase = true)
                    }
                    adapter.updateList(filteredList)
                }
                return true
            }
        })
    }

    private fun getImages(): List<Int> {
        return listOf(R.drawable.fruit, R.drawable.nature, R.drawable.transport, R.drawable.animals, R.drawable.musical, R.drawable.technology)
    }

    private fun setupFloatingActionButton() {
        floatingActionButton.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_dialog, null)
            bottomSheetDialog.setContentView(bottomSheetView)

            val bottomSheetDialogWindow = bottomSheetDialog.window
            bottomSheetDialogWindow?.setBackgroundDrawableResource(android.R.color.transparent)

            val statsTextView = bottomSheetView.findViewById<TextView>(R.id.statsTextView)

            val currentItems = adapter.getList()

            val itemCount = currentItems.size

            val characterCounts = currentItems
                .joinToString("") { it.first.first.filter { char -> char.isLetter() } }
                .groupingBy { it }
                .eachCount()

            val sortedCounts = characterCounts.entries.sortedByDescending { it.value }.take(3)

            val statsText = StringBuilder()

            val listSizeText = "List Size: $itemCount\n"
            statsText.append(listSizeText)

            val topOccurrencesText = "Top 3 Occurrences:\n"
            statsText.append(topOccurrencesText)

            sortedCounts.forEach { entry ->
                statsText.append("${entry.key} = ${entry.value}\n")
            }

            val spannable = SpannableString(statsText.toString())
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, listSizeText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), listSizeText.length, listSizeText.length + topOccurrencesText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            statsTextView.text = spannable

            bottomSheetDialog.show()
        }
    }
}
