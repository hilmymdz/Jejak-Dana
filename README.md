# Jejak Dana - Aplikasi Pelacak Keuangan Pribadi

**Jejak Dana** adalah sebuah aplikasi Android modern yang dirancang untuk membantu pengguna melacak dan mengelola keuangan pribadi mereka dengan mudah dan efisien. Aplikasi ini dibangun menggunakan Kotlin dan komponen Android Jetpack modern, dengan fokus pada antarmuka yang bersih dan pengalaman pengguna yang intuitif.

## Tampilan Aplikasi

| Halaman Utama | Halaman Anggaran | Halaman Pengaturan |
| :---: | :---: | :---: |
| ![Tampilan Halaman Utama](<img width="453" height="1004" alt="image" src="https://github.com/user-attachments/assets/6f8beea5-9dfd-4b68-b2a3-d4810952ed24" />)
| ![Tampilan Halaman Anggaran](<img width="454" height="1004" alt="image" src="https://github.com/user-attachments/assets/39046e47-3bab-4a1b-b3c5-2a516ef39985" />) 
| ![Tampilan Halaman Pengaturan](<img width="453" height="1004" alt="image" src="https://github.com/user-attachments/assets/b1d7e653-b76a-473f-aa2f-ce7e0936a964" />) |


## Fitur Utama
- **Pencatatan Transaksi:** Catat pemasukan dan pengeluaran dengan cepat melalui formulir yang mudah diisi.
- **Ringkasan Keuangan:** Lihat ringkasan total pemasukan, pengeluaran, dan saldo akhir secara langsung di halaman utama.
- **Manajemen Anggaran:** Buat anggaran kustom untuk berbagai kategori pengeluaran (misalnya, Makanan, Transportasi) dengan periode mingguan, bulanan, atau tahunan.
- **Progress Bar Dinamis:** Pantau penggunaan anggaran Anda secara visual melalui progress bar yang diperbarui secara *real-time* setiap kali ada pengeluaran baru.
- **Riwayat Transaksi:** Lihat daftar semua transaksi yang telah Anda catat, lengkap dengan ikon kategori untuk identifikasi cepat.
- **Pengaturan Data:** Kelola data Anda dengan opsi untuk menghapus semua transaksi atau semua anggaran untuk memulai dari awal.

## Teknologi dan Arsitektur
Aplikasi ini dibangun dengan mengikuti praktik terbaik pengembangan Android modern.
- **Bahasa:** [Kotlin](https://kotlinlang.org/)
- **Arsitektur:** MVVM (Model-View-ViewModel)
- **Komponen Inti Jetpack:**
    - **UI Layer:** [Fragments](https://developer.android.com/guide/fragments) & [XML Layouts](https://developer.android.com/guide/topics/ui/declaring-layout)
    - **Navigation:** [Navigation Component](https://developer.android.com/guide/navigation) untuk mengelola alur antar layar.
    - **Database:** [Room Persistence Library](https://developer.android.com/training/data-storage/room) sebagai lapisan abstraksi di atas database SQLite lokal.
    - **Lifecycle & State Management:** [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) dan [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) untuk mengelola data UI yang sadar siklus hidup (*lifecycle-aware*).
- **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) untuk menangani operasi database di *background thread*.
- **Tampilan Daftar:** [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) untuk menampilkan daftar transaksi dan anggaran secara efisien.
