import { useState } from 'react'
import { useHistory, Link } from 'react-router-dom';
//import API from '../../services/api';
import '../signin/styles.css'
import Lock from '../../assets/images/padlock.png'

import '../../services/api'

import { MdLock, MdForum } from "react-icons/md"
import { HiEye, HiEyeOff } from "react-icons/hi"

export default function SignIn() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")

  const history = useHistory();

  async function login(e: any) {
    e.preventDefault();
    history.push('/calendar');
    //const data = {
    //  'user_email': email,
    //  'password': password,
    //};

    //try {
    //  const response = await API.post('/auth/signin', data);
    //  localStorage.setItem('email', email);
    //  localStorage.setItem('toker', response.data.token);

    //  history.push('/calendar');
    //} catch (error) {
    //  alert("Autenticação sem sucesso, tente novamente!\n" + error)
    //}
  }

  const [show, setShow] = useState(false)

  const togglePassword = (e: any) => {
    e.preventDefault()
    setShow(!show);
  }

  return (
    <>
      <div className="login">
        <div className="login-logo">
          <img
            src={Lock}
            alt="MdLockLogin App"
          />
        </div>

        <form onSubmit={login}>
          <div className="login-right">
            <h1>A U T E N T I C A Ç Ã O</h1>

            <div className="login-input-box">
              <MdForum />
              <input
                type="email"
                placeholder="Digite seu email"
                value={email}
                onChange={e => setEmail(e.target.value)}
              />
            </div>

            <div className="login-input-box">
              <MdLock />
              <input
                placeholder="Digite sua senha"
                type={show ? "text" : "password"}
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
              <div className="login-eye">
                {show ? (
                  <HiEye
                    size={20}
                    onClick={togglePassword}
                  />
                ) : (
                  <HiEyeOff
                    size={20}
                    onClick={togglePassword}
                  />
                )}
              </div>
            </div>

            <div className="form-confirm">
              <Link to="/signup">
                Criar Conta
              </Link>
              <div className="middle-space" />
              <button type="submit">
                E N T R A R
              </button>
            </div>
          </div>
        </form>
      </div>
    </>
  )
}
