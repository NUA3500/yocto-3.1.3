SUMMARY = "vc8000.ko for kernel"
SECTION = "modules"
LICENSE = "CLOSED"

KV = "5.4.50"

SRC_URI += " \
    file://vc8000.ko \
    "
do_package_qa[noexec] = "1"
do_install() {
    install -d ${D}/${base_libdir}/modules/${KV}
    install -m 0644 ${WORKDIR}/vc8000.ko ${D}/${base_libdir}/modules/${KV}/vc8000.ko
}

FILES_SOLIBSDEV = ""
FILES_${PN} = "${base_libdir}/modules/${KV}/vc8000.ko"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ma35d1)"
