class JsPlatform: Platform {
    override val name: String = "Javascript : Web platform"
}

actual fun getPlatform(): Platform = JsPlatform()