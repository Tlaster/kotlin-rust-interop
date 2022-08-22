use std::ffi::{CStr, CString};

use libc::c_char;

#[no_mangle]
pub extern "C" fn rusthello(input: *const c_char) -> *const c_char {
    let c_str = unsafe { CStr::from_ptr(input) };
    let input = c_str.to_str().unwrap();
    let output = format!("Hello from rust, {}!", input);
    CString::new(output).unwrap().into_raw()
}